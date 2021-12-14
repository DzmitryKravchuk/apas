package com.belpost.apas.service.xlsx;

import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsxFileReader implements AutoCloseable {
    private final InputStream inputStream;
    private final CustomObjectMapper objectMapper;
    private final WorkSheetReader reader;

    public <T> DataModel read(File fileToRead, Class<T> clazz) throws IOException {
        log.info("Start reading workbook from file [{}]", fileToRead);
        DataModel <T> dataModel = new DataModel<>();
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
        XSSFSheet workSheet = workBook.getSheetAt(0);

        //process metadata
        String metadataAsString = reader.readCellValue(workSheet, 0, 0);
        processMetadata(dataModel, metadataAsString);

        //process header
        final Map<Integer, String> header = getHeaderAsMap(workSheet);

        //process content
        int lastRowIndex = workSheet.getLastRowNum();
        for (int i = 2; i <= lastRowIndex; i++) {
            XSSFRow nextRow = workSheet.getRow(i);
            String json = convertRowToStringObject(header, nextRow);
            final T pojo = objectMapper.readFromFile(json, clazz);
            dataModel.getContent().add(pojo);
        }

        log.info("Finish reading from file [{}]", fileToRead);

        return dataModel;
    }

    private String convertRowToStringObject(Map<Integer, String> header,
                                            XSSFRow nextRow) throws JsonProcessingException {
        Iterator<Cell> cellIter = nextRow.cellIterator();
        Map<String, String> rowObjectMap = new HashMap<>();
        while(cellIter.hasNext()) {
            XSSFCell nextCell = (XSSFCell) cellIter.next();
            rowObjectMap.put(header.get(nextCell.getColumnIndex()),
                reader.readCellValue(nextCell));
        }
        return  objectMapper.writeValueAsString(rowObjectMap);
    }

    private Map<Integer, String> getHeaderAsMap(XSSFSheet workSheet) {
        final Map<Integer, String> header = new HashMap<>();
        XSSFRow nextRow = workSheet.getRow(1);
        Iterator<Cell> cellIter = nextRow.cellIterator();
        while (cellIter.hasNext()) {
            XSSFCell nextCell = (XSSFCell) cellIter.next();
            header.put(nextCell.getColumnIndex(), reader.readCellValue(nextCell));
        }
        return header;
    }

    private <T> void processMetadata(DataModel<T> dataModel, String metadataAsString) {
        String[] keyVals = metadataAsString.split(",");
        for(String keyVal:keyVals)
        {
            String[] parts = keyVal.split(":",2);
            dataModel.getMetadata().put(parts[0],parts[1]);
        }
    }


    @Override
    public void close() throws Exception {
        inputStream.close();
    }
}
