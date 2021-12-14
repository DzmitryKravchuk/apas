package com.belpost.apas.service.xlsx;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {XlsxFileReader.class,
    ObjectMapper.class,
    CustomObjectMapper.class})
class XlsxFileReaderTest {

    private static final String officeFile = "src/test/resources/xlsx/office.xlsx";

    @Autowired
    private XlsxFileReader fileReader;

    @Autowired
    private CustomObjectMapper customObjectMapper;

    @Test
    void shouldReadFromOfficesToDataModel(){
         DataModel <OfficeModel> model;
    }
}