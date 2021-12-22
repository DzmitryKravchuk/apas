package com.belpost.apas.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.OfficeType;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {OfficeTypeMapperImpl.class,
    ObjectMapper.class,
    CustomObjectMapper.class})
class OfficeTypeMapperTest {
    private static OfficeType entity;
    private static OfficeTypeModel model;

    @Autowired
    private OfficeTypeMapper officeTypeMapper;

    @Autowired
    private CustomObjectMapper customObjectMapper;

    @BeforeEach
    void setUp() throws IOException {
        entity = customObjectMapper
            .readFromFile("src/test/resources/json/officeType/officeTypePostOffice.json", OfficeType.class);
        model = customObjectMapper
            .readFromFile("src/test/resources/json/officeType/officeTypePostOffice.json", OfficeTypeModel.class);
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
