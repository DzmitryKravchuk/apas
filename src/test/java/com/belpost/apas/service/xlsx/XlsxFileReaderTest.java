package com.belpost.apas.service.xlsx;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest()
class XlsxFileReaderTest {

    private static final String officeFile = "src/test/resources/xlsx/office.xls";
    private final XlsxFileReader fileReader = new XlsxFileReader(officeFile);

    XlsxFileReaderTest() throws FileNotFoundException {
    }


    @Test
    void shouldReadFromOfficesToDataModel() throws IOException {
        DataModel<OfficeModel> model = fileReader.read(OfficeModel.class);
        Assertions.assertEquals(2, model.getContent().size());
    }
}