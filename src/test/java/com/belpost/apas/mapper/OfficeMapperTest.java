package com.belpost.apas.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {OfficeMapperImpl.class,
    ObjectMapper.class,
    CustomObjectMapper.class})
class OfficeMapperTest {

    private static Office entity;
    private static Office parent;
    private static OfficeModel model;
    private static OfficeModel parentModel;
    private static OfficeTypeModel officeTypeModel;

    @Autowired
    private OfficeMapper officeMapper;

    @Autowired
    private CustomObjectMapper customObjectMapper;

    @BeforeEach
    void setUp() throws IOException {
        entity = customObjectMapper
            .readFromFile("src/test/resources/json/postOffice.json", Office.class);
        parent = customObjectMapper
            .readFromFile("src/test/resources/json/postOfficeParent.json", Office.class);
        model = customObjectMapper
            .readFromFile("src/test/resources/json/postOfficeModel.json", OfficeModel.class);
        parentModel = customObjectMapper
            .readFromFile("src/test/resources/json/postOfficeModelParent.json", OfficeModel.class);
        officeTypeModel = customObjectMapper
            .readFromFile("src/test/resources/json/officeTypePostOffice.json", OfficeTypeModel.class);
    }

    @Test
    void shouldMapEntityToModel() {
        OfficeModel actual = officeMapper.mapToModel(entity, parent);

        assertEquals(model, actual);
    }

    @Test
    void shouldMapModelToEntity() {
        Office actual = officeMapper.mapToEntity(model, parentModel, officeTypeModel);

        assertEquals(entity, actual);
    }
}