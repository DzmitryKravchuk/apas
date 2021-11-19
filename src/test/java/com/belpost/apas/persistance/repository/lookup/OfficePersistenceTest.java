package com.belpost.apas.persistance.repository.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.persistance.entity.Office;
import com.belpost.apas.persistance.repository.OfficeRepository;
import com.belpost.apas.support.PersistenceTest;
import com.github.database.rider.core.api.dataset.DataSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@PersistenceTest
class OfficePersistenceTest {

    private static final String OFFICE_CODE = "231000";

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
        Office ot = repository.findByCode(OFFICE_CODE)
            .orElseThrow(() -> new ResourceNotFoundException("Failed to execute OfficeRepositoryTest"));

        assertEquals(OFFICE_CODE, ot.getCode());
    }

    @Test
    void shouldGetAllOffices() {
        List<Office> l = repository.findAll();

        assertEquals(21, l.size());
    }

}
