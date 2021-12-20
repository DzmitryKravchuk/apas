package com.belpost.apas.service.xlsx;

import static org.assertj.core.api.Assertions.assertThat;

import com.belpost.apas.model.OfficeModel;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {XlsxFileReader.class,
    WorkSheetReader.class})
class XlsxFileReaderTest {

    private static final String officeFile = "src/test/resources/xlsx/office.xlsx";
    private static final Map<String, String> expectedMap = Map.of("INPUT", "officeData", "TYPE", "initial");

    @Autowired
    private XlsxFileReader fileReader;


    @Test
    void shouldReadFromOfficesToDataModel()
        throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException,
        InstantiationException {
        DataModel<OfficeModel> model = fileReader.read(officeFile, OfficeModel.class);

        assertThat(model.getMetadata())
            .hasSize(2)
            .isEqualTo(expectedMap);

        assertThat(model.getContent())
            .hasSize(2)
            .satisfiesExactlyInAnyOrder(
                o -> assertThat(o.getOfficeTypeCode()).isEqualTo("РайУч"),
                o -> assertThat(o.getOfficeTypeCode()).isEqualTo("ОПС"));
    }
}