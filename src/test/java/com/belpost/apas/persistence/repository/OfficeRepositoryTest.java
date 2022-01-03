package com.belpost.apas.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.persistence.entity.Office;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:dataset/office/office_data.sql"})
class OfficeRepositoryTest extends AbstractPersistenceTest {

    private static final String OFFICE_CODE = "231000";
    private static final Long OFFICE_ID = 2L;

    @Test
    void shouldGetOfficeByCode() {
        Office ot = officeRepository.findByCode(OFFICE_CODE)
            .orElseThrow(() -> new ResourceNotFoundException("Failed to execute OfficeRepositoryTest"));

        assertEquals(OFFICE_CODE, ot.getCode());
    }

    @Test
    void shouldGetOfficeById() {
        Office ot = officeRepository.findById(OFFICE_ID)
            .orElseThrow(() -> new ResourceNotFoundException("Failed to execute OfficeRepositoryTest"));

        assertEquals(OFFICE_ID, ot.getId());
    }

    @Test
    void shouldGetAllOffices() {
        List<Office> l = officeRepository.findAll();

        assertEquals(21, l.size());
    }

    @Test
    void shouldGetAllOfficesByParentId() {
        List<Office> l1 = officeRepository.findAllByParentIdIn(1L);
        List<Office> l2 = officeRepository.findAllByParentIdIn(2L, 3L);
        List<Office> l3 = officeRepository.findAllByParentIdIn(20L, 21L);

        assertEquals(2, l1.size());
        assertEquals(4, l2.size());
        assertEquals(0, l3.size());

    }

}
