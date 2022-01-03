package com.belpost.apas.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.OfficeType;
import com.belpost.apas.persistence.repository.OfficeTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class OfficeTypeServiceIntegrationTest {
    private static final String OFFICE_TYPE_CODE = OfficeTypeModel.ROOT_OFFICE_CODE;

    @Autowired
    private OfficeTypeServiceImpl service;

    @Autowired
    OfficeTypeRepository repository;

    @Test
    void shouldGetByCode() {
        OfficeType ot = repository.findByCode(OfficeTypeModel.ROOT_OFFICE_CODE).orElse(null);
        OfficeTypeModel model = service.findByCode(OFFICE_TYPE_CODE);
        assertNotNull(model);
    }
}