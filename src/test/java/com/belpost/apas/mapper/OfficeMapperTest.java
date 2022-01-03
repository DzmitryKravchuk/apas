package com.belpost.apas.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.model.OfficeModel;
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

    private static final String OFFICE_TYPE_CODE = "ОПС";
    private static final String PARENT_OFFICE_CODE = "231100";
    private static final Integer HIERARCHY_LVL = 1;
    private static final Long OFFICE_TYPE_ID = 2L;
    private static Office entity;
    private static OfficeModel model;

    @Autowired
    private OfficeMapper officeMapper;

    @Autowired
    private CustomObjectMapper customObjectMapper;

    @BeforeEach
    void setUp() throws IOException {
        entity = customObjectMapper
            .readFromFile("src/test/resources/json/office/postOffice.json", Office.class);
        model = customObjectMapper
            .readFromFile("src/test/resources/json/office/postOfficeModel.json", OfficeModel.class);
    }

    @Test
    void shouldMapEntityToModel() {
        OfficeModel actual = officeMapper.mapToModel(entity, OFFICE_TYPE_CODE, PARENT_OFFICE_CODE, HIERARCHY_LVL);

        assertEquals(model, actual);
    }

    @Test
    void shouldMapModelToEntity() {
        Office actual = officeMapper.mapToEntity(model, OFFICE_TYPE_ID);

        assertEquals(entity, actual);
    }
}