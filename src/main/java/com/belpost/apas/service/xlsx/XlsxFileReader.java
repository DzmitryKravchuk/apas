package com.belpost.apas.service.xlsx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsxFileReader {

    private final WorkSheetReader reader;

    private XSSFWorkbook createWorkBook(String pathToFile) throws IOException {
        File fileToRead = new File(pathToFile);
        try (InputStream inputStream = new FileInputStream(fileToRead)) {
            return new XSSFWorkbook(inputStream);
        }
    }


    public <T> DataModel<T> read(String pathToFile, Class<T> clazz) throws IOException {
        log.info("Start reading workbook from file [{}]", pathToFile);
        XSSFWorkbook workBook = createWorkBook(pathToFile);
        XSSFSheet workSheet = workBook.getSheetAt(0);

        DataModel<T> dataModel = reader.read(workSheet, clazz);

        log.info("Finish reading from file [{}]", pathToFile);
        return dataModel;
    }

}
