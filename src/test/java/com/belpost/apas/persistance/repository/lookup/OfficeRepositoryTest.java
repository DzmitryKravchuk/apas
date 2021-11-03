package com.belpost.apas.persistance.repository.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.persistance.entity.lookup.Office;
import com.belpost.apas.support.RepositoryTest;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class OfficeRepositoryTest {

    private static final String POST_OFFICE_CODE = "230000";

    @Autowired
    OfficeRepository repository;

    @BeforeEach
    @DataSet(value = {"/dataset/officeType/officeType.yml",
        "/dataset/office/office.yml"},
        cleanBefore = true, useSequenceFiltering = false)
    public void setUp() {
    }


    @Test
    void shouldGetOfficeByCode() {
        Office ot = repository.findByCode(POST_OFFICE_CODE)
            .orElseThrow(() -> new ResourceNotFoundException("Failed to execute OfficeRepositoryTest"));

        assertEquals(POST_OFFICE_CODE, ot.getCode());
    }
}