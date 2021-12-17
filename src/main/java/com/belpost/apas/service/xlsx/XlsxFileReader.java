package com.belpost.apas.service.xlsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
public class XlsxFileReader implements AutoCloseable {
    private final InputStream inputStream;
    private final File fileToRead;
    private final String pathToFile;


    public XlsxFileReader(String pathToFile) throws FileNotFoundException {
        this.pathToFile = pathToFile;
        this.fileToRead = new File(pathToFile);
        this.inputStream = new FileInputStream(fileToRead);
    }




    public <T> DataModel<T> read(Class<T> clazz) throws IOException {
        log.info("Start reading workbook from file [{}]", pathToFile);
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
        XSSFSheet workSheet = workBook.getSheetAt(0);

        WorkSheetReader reader = new WorkSheetReader(new CustomObjectMapper(new ObjectMapper()));
        DataModel<T> dataModel = reader.read(workSheet, clazz);

        log.info("Finish reading from file [{}]", pathToFile);
        return dataModel;
    }

    @Override
    public void close() throws Exception {
        inputStream.close();
    }
}
