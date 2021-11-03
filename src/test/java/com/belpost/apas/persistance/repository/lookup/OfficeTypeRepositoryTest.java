package com.belpost.apas.persistance.repository.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.model.lookup.OfficeTypeModel;
import com.belpost.apas.persistance.entity.lookup.OfficeType;
import com.belpost.apas.support.RepositoryTest;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class OfficeTypeRepositoryTest {

    private static final String OFFICE_TYPE_CODE = OfficeTypeModel.ROOT_OFFICE_CODE;

    @Autowired
    OfficeTypeRepository repository;

    @Test
    @ExpectedDataSet(value = "/dataset/officeType/officeType.yml")
    void shouldGetOfficeTypeByCode() {
        OfficeType ot = repository.findByCode(OfficeTypeModel.ROOT_OFFICE_CODE)
            .orElseThrow(() -> new ResourceNotFoundException("Failed to execute OfficeTypeRepositoryTest"));

        assertEquals(OFFICE_TYPE_CODE, ot.getCode());
        assertEquals(5L,ot.getId());
    }

}