package com.belpost.apas.persistence.repository.lookup;

import com.belpost.apas.config.JdbcConfig;
import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.OfficeType;
import com.belpost.apas.persistence.repository.OfficeTypeRepository;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.List;

@DataJdbcTest
//@Sql({"classpath:schema.sql"})
@ActiveProfiles("test")
//@DBRider
//@ContextConfiguration(classes = JdbcConfig.class)
class OfficeTypePersistenceTest {

    private static final String OFFICE_TYPE_CODE = OfficeTypeModel.ROOT_OFFICE_CODE;

    @Autowired
    private OfficeTypeRepository repository;

    private static final CustomObjectMapper customObjectMapper = new CustomObjectMapper(new ObjectMapper());

    @Test
    void shouldGetOfficeTypeByCode() {

        //String sql = "SELECT * FROM office_type";
        //List<OfficeType> type = jdbcTemplate.query(
        //        sql, new BeanPropertyRowMapper(OfficeType.class));

        OfficeType ot = repository.findByCode(OfficeTypeModel.ROOT_OFFICE_CODE)
                .orElseThrow(() -> new ResourceNotFoundException("Failed to execute OfficeTypeRepositoryTest"));

        Assertions.assertThat(ot.getCode()).isEqualTo(OFFICE_TYPE_CODE);
        Assertions.assertThat(ot.getId()).isEqualTo(5L);
    }

    @Test
    void shouldGetAllOfficeTypes() throws IOException {
        List<OfficeType> actual = repository.findAll();
        List<OfficeType> expected = customObjectMapper
                .readListFromFile("src/test/resources/json/officeTypeAll.json", OfficeType.class);

        Assertions.assertThat(actual)
                .hasSize(5)
                .containsExactlyInAnyOrderElementsOf(expected);
    }
}
