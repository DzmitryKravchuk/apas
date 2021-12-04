package com.belpost.apas.service.xlsx;

import com.belpost.apas.service.util.CustomObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsxFileReader implements AutoCloseable {
    private final InputStream inputStream;
    private final CustomObjectMapper objectMapper;

    public <T> List<T> read(File fileToRead, Class<T> clazz) throws IOException {
        log.info("Start reading workbook from file [{}]", fileToRead);
        List<T> list = new ArrayList<>();
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
        XSSFSheet workSheet = workBook.getSheetAt(0);
        WorkSheetReader reader = new WorkSheetReader(workSheet);

        //process metadata
        String metadata = reader.readCellValue(0, 0);

        //process header
        final Map<Integer, String> header = new HashMap<>();
        XSSFRow nextRow = workSheet.getRow(1);
        Iterator<Cell> cellIter = nextRow.cellIterator();
        while (cellIter.hasNext()) {
            XSSFCell nextCell = (XSSFCell) cellIter.next();
            header.put(nextCell.getColumnIndex(), nextCell.getStringCellValue());
        }

        //process content
        nextRow = workSheet.getRow(2);
        cellIter = nextRow.cellIterator();
        int lastRowIndex = workSheet.getLastRowNum();
        Map<String, String> rowObjectMap = new HashMap<>();
        for (int i = 2; i <= lastRowIndex; i++) {
            XSSFCell nextCell = (XSSFCell) cellIter.next();
            rowObjectMap.put(header.get(nextCell.getColumnIndex()),
                    nextCell.getStringCellValue());
        }
        String json = objectMapper.writeValueAsString(rowObjectMap);
        final T pojo = objectMapper.readFromFile(json, clazz);
        list.add(pojo);

        log.info("Finish reading from file [{}]", fileToRead);

        return list;
    }


    @Override
    public void close() throws Exception {
        inputStream.close();
    }
}
