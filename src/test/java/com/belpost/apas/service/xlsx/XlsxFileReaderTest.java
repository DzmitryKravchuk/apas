package com.belpost.apas.service.xlsx;

import com.belpost.apas.model.OfficeModel;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {XlsxFileReader.class,
        WorkSheetReader.class})
class XlsxFileReaderTest {

    private static final String officeFile = "src/test/resources/xlsx/office.xlsx";

    @Autowired
    private XlsxFileReader fileReader;


    @Test
    void shouldReadFromOfficesToDataModel()
        throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException,
        InstantiationException {
        DataModel<OfficeModel> model = fileReader.read(officeFile, OfficeModel.class);
        Assertions.assertEquals(2, model.getContent().size());
    }
}