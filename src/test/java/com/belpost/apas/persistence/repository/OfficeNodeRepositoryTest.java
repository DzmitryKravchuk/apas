package com.belpost.apas.persistence.repository;

import com.belpost.apas.persistence.entity.Office;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Sql({"classpath:dataset/office/office_data.sql"})
class OfficeNodeRepositoryTest extends  AbstractPersistenceTest{


    @Test
    void shouldGetAllOfficesByParentId() {
        List<Office> l1 = officeNodeRepository.findAllByParentOfficeIdIn(1L);
        List<Office> l2 = officeNodeRepository.findAllByParentOfficeIdIn(2L, 3L);

        assertEquals(2, l1.size());
        assertEquals(4, l2.size());
    }
}