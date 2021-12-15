package com.belpost.apas.service.xlsx;

import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorkSheetReader {

    private final CustomObjectMapper objectMapper;

    public <T> DataModel<T> read(XSSFSheet workSheet, Class<T> clazz) throws IOException {
        log.info("Start reading from workSheet [{}]", workSheet.getSheetName());
        DataModel<T> dataModel = new DataModel<>();
        //process metadata
        String metadataAsString = readCellValue(workSheet, 0, 0);
        processMetadata(dataModel, metadataAsString);

        //process header
        final Map<Integer, String> header = getHeaderAsMap(workSheet);

        //process content
        fillContent(dataModel, workSheet, clazz, header);

        log.info("Finish reading from workSheet [{}]", workSheet.getSheetName());

        return dataModel;
    }

    private <T> void fillContent(DataModel<T> dataModel, XSSFSheet workSheet, Class<T> clazz, Map<Integer, String> header) throws IOException {
        int lastRowIndex = workSheet.getLastRowNum();
        for (int i = 2; i <= lastRowIndex; i++) {
            XSSFRow nextRow = workSheet.getRow(i);
            String json = convertRowToStringObject(header, nextRow);
            final T pojo = objectMapper.readFromFile(json, clazz);
            dataModel.getContent().add(pojo);
        }
    }

    private String convertRowToStringObject(Map<Integer, String> header,
                                            XSSFRow nextRow) throws JsonProcessingException {
        Iterator<Cell> cellIter = nextRow.cellIterator();
        Map<String, String> rowObjectMap = new HashMap<>();
        while (cellIter.hasNext()) {
            XSSFCell nextCell = (XSSFCell) cellIter.next();
            rowObjectMap.put(header.get(nextCell.getColumnIndex()),
                    readCellValue(nextCell));
        }
        return objectMapper.writeValueAsString(rowObjectMap);
    }

    private Map<Integer, String> getHeaderAsMap(XSSFSheet workSheet) {
        final Map<Integer, String> header = new HashMap<>();
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

    private String readCellValue(Cell nextCell) {
        return nextCell.getStringCellValue();
    }

}
