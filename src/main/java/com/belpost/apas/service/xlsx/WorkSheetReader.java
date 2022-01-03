package com.belpost.apas.service.xlsx;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorkSheetReader {

    public <T> DataModel<T> read(XSSFSheet workSheet, Class<T> clazz)
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
        InstantiationException {
        log.info("Start reading from workSheet [{}]", workSheet.getSheetName());
        DataModel<T> dataModel = new DataModel<>();
        //process metadata
        String metadataAsString = readCellValue(workSheet, 0, 0);
        processMetadata(dataModel, metadataAsString);

        //process header
        final Map<Integer, Object> header = getHeaderAsMap(workSheet);

        //process content
        fillContent(dataModel, workSheet, clazz, header);

        log.info("Finish reading from workSheet [{}]", workSheet.getSheetName());

        return dataModel;
    }

    private <T> void fillContent(DataModel<T> dataModel, XSSFSheet workSheet, Class<T> clazz,
                                 Map<Integer, Object> header)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException,
        InstantiationException {
        int lastRowIndex = workSheet.getLastRowNum();
        for (int i = 2; i <= lastRowIndex; i++) {
            XSSFRow nextRow = workSheet.getRow(i);
            final T pojo = convertRowToObject(header, nextRow, clazz);
            dataModel.getContent().add(pojo);
        }
    }

    private <T> T convertRowToObject(Map<Integer, Object> header, XSSFRow nextRow, Class<T> clazz)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Iterator<Cell> cellIter = nextRow.cellIterator();
        Constructor<T> cst = clazz.getConstructor();
        T pojo = cst.newInstance();
        Map<String, Method> methods = getMethods(clazz);
        methods.putAll(getMethods(clazz.getSuperclass()));
        while (cellIter.hasNext()) {
            XSSFCell nextCell = (XSSFCell) cellIter.next();
            Method m = methods.get("set" + header.get(nextCell.getColumnIndex()));

            m.invoke(pojo, readCellValue(nextCell));
        }
        return pojo;
    }

    private <T> Map<String, Method> getMethods(Class<T> clazz) {
        Map<String, Method> methods = Arrays.stream(clazz.getDeclaredMethods())
            .collect(Collectors.toMap(Method::getName, m -> m));
        if (clazz.getSuperclass() != null) {
            Map<String, Method> superMethods = Arrays.stream(clazz.getSuperclass().getDeclaredMethods())
                .collect(Collectors.toMap(Method::getName, m -> m));
            methods.putAll(superMethods);
        }
        return methods;
    }

    private Map<Integer, Object> getHeaderAsMap(XSSFSheet workSheet) {
        final Map<Integer, Object> header = new HashMap<>();
        XSSFRow nextRow = workSheet.getRow(1);
        Iterator<Cell> cellIter = nextRow.cellIterator();
        while (cellIter.hasNext()) {
            XSSFCell nextCell = (XSSFCell) cellIter.next();
            header.put(nextCell.getColumnIndex(), readCellValue(nextCell));
        }
        return header;
    }

    private <T> void processMetadata(DataModel<T> dataModel, String metadataAsString) {
        String[] keyVals = metadataAsString.split(",");
        for (String keyVal : keyVals) {
            String[] parts = keyVal.split(":", 2);
            dataModel.getMetadata().put(parts[0], parts[1]);
        }
    }

    private String readCellValue(XSSFSheet workSheet, int rowIndex, int columnIndex) {
        XSSFRow nextRow = workSheet.getRow(rowIndex);
        Cell nextCell = nextRow.getCell(columnIndex);
        return nextCell.getStringCellValue();
    }

    private Object readCellValue(Cell nextCell) {
        return (nextCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            ? (long) nextCell.getNumericCellValue() : nextCell.getStringCellValue();
    }

}
