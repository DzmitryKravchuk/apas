package com.belpost.apas.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.persistance.entity.Office;
import com.belpost.apas.utils.JsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {OfficeMapperImpl.class,
    OfficeTypeMapperImpl.class,
    ObjectMapper.class,
    JsonMapper.class})
class OfficeMapperTest {

    private static Office entity;
    private static OfficeModel model;

    @Autowired
    private OfficeMapper officeMapper;

    @Autowired
    private JsonMapper jsonMapper;

    @BeforeEach
    void setUp() throws IOException {
        entity = jsonMapper
            .readFromFile("src/test/resources/json/postOffice.json", Office.class);
        model = jsonMapper
            .readFromFile("src/test/resources/json/postOfficeModel.json", OfficeModel.class);
    }

    @Test
    void shouldMapEntityToModel() {
        OfficeModel actual = officeMapper.mapToModel(entity);

        assertEquals(model, actual);
    }

    @Test
    void shouldMapModelToEntity() {
        Office actual = officeMapper.mapToEntity(model);

        assertEquals(entity, actual);
    }
}