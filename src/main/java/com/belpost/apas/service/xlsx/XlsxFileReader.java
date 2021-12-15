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
    private final WorkSheetReader reader;

    public <T> DataModel<T> read(File fileToRead, Class<T> clazz) throws IOException {
        log.info("Start reading workbook from file [{}]", fileToRead);
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
        XSSFSheet workSheet = workBook.getSheetAt(0);

        DataModel<T> dataModel = reader.read(workSheet, clazz);

        log.info("Finish reading from file [{}]", fileToRead);
        return dataModel;
    }

    @Override
    public void close() throws Exception {
        inputStream.close();
    }
}
