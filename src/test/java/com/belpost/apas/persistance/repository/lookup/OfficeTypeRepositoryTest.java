package com.belpost.apas.persistance.repository.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.config.TestContainerConfig;
import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.model.lookup.OfficeTypeModel;
import com.belpost.apas.persistance.entity.lookup.OfficeType;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestContainerConfig.class)
@SpringBootTest
@DBRider
class OfficeTypeRepositoryTest {

    private static final String POST_OFFICE_CODE = OfficeTypeModel.POST_OFFICE_CODE;

    @Autowired
    OfficeTypeRepository repository;

    @Test
    void shouldGetOfficeTypeByCode() {
        OfficeType ot = repository.findByCode(OfficeTypeModel.POST_OFFICE_CODE)
            .orElseThrow(() -> new ResourceNotFoundException("Failed to execute OfficeTypeRepositoryTest"));

        assertEquals(POST_OFFICE_CODE, ot.getCode());
    }

}