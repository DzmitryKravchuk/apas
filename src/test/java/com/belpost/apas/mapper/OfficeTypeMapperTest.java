package com.belpost.apas.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistance.entity.OfficeType;
import com.belpost.apas.utils.JsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {OfficeTypeMapperImpl.class,
    ObjectMapper.class,
    JsonMapper.class})
class OfficeTypeMapperTest {
    private static OfficeType entity;
    private static OfficeTypeModel model;

    @Autowired
    private OfficeTypeMapper officeTypeMapper;

    @Autowired
    private JsonMapper jsonMapper;

    @BeforeEach
    void setUp() throws IOException {
        entity = jsonMapper
            .readFromFile("src/test/resources/json/officeTypePostOffice.json", OfficeType.class);
        model = jsonMapper
            .readFromFile("src/test/resources/json/officeTypePostOffice.json", OfficeTypeModel.class);
    }

    @Test
    void shouldMapEntityToModel() {
        OfficeTypeModel actual = officeTypeMapper.mapToModel(entity);

        assertEquals(model, actual);
    }

    @Test
    void shouldMapModelToEntity() {
        OfficeType actual = officeTypeMapper.mapToEntity(model);

        assertEquals(entity, actual);
    }

}
