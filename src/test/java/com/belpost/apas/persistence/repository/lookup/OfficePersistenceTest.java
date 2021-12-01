package com.belpost.apas.persistence.repository.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.OfficeRepository;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

//@PersistenceTest
@DBRider
//@ActiveProfiles("test")
@DataJdbcTest
//@EnableJdbcRepositories(basePackages="com.belpost.apas.persistance.repository")
//@SpringBootTest(classes = {Office.class,
//    OfficeRepository.class
//    RelationalMappingContext.class,
//    BasicJdbcConverter.class,
//    H2Dialect.class
//})
class OfficePersistenceTest {

    private static final String OFFICE_CODE = "231000";

    @Autowired
    OfficeRepository repository;

//    @Autowired
//    BasicJdbcConverter jdbcConverter;

 //   @Autowired
 //   H2Dialect dialect;

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
