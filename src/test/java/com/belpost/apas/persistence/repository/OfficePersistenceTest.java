package com.belpost.apas.persistence.repository;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.persistence.entity.Office;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql({"classpath:dataset/office/office_data.sql"})
class OfficePersistenceTest extends AbstractPersistenceTest {

    private static final String OFFICE_CODE = "231000";

    @Autowired
    OfficeRepository repository;

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

    @Test
    void shouldGetAllOfficesByParentId() {
        List<Office> l1 = repository.findAllByParentId(1L);
        List<Office> l2 = repository.findAllByParentId(2L, 3L);

        assertEquals(2, l1.size());
        assertEquals(4, l2.size());
    }

}
