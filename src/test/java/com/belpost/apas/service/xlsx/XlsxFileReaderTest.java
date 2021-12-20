package com.belpost.apas.service.xlsx;

import com.belpost.apas.mapper.OfficeMapperImpl;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(classes = {XlsxFileReader.class,
        WorkSheetReader.class,
        ObjectMapper.class,
        CustomObjectMapper.class})
class XlsxFileReaderTest {

    private static final String officeFile = "src/test/resources/xlsx/office.xlsx";

    @Autowired
    private XlsxFileReader fileReader;


    @Test
    void shouldReadFromOfficesToDataModel() throws IOException {
        DataModel<OfficeModel> model = fileReader.read(officeFile, OfficeModel.class);
        Assertions.assertEquals(2, model.getContent().size());
    }
}