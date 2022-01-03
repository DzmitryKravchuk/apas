package com.belpost.apas.persistence.repository;

import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJdbcTest
public class AbstractPersistenceTest {
    protected static final CustomObjectMapper customObjectMapper = new CustomObjectMapper(new ObjectMapper());

    @Autowired
    protected OfficeTypeRepository officeTypeRepository;

    @Autowired
    protected OfficeRepository officeRepository;



    //String sql = "SELECT * FROM \"office\"";
    //List<Office> type = jdbcTemplate.query(
    //        sql, new BeanPropertyRowMapper(Office.class));
}
