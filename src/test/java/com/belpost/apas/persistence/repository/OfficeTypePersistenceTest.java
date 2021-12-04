package com.belpost.apas.persistence.repository;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.OfficeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

class OfficeTypePersistenceTest extends AbstractPersistenceTest {

    private static final String OFFICE_TYPE_CODE = OfficeTypeModel.ROOT_OFFICE_CODE;

    @Autowired
    private OfficeTypeRepository repository;

    @Test
    void shouldGetOfficeTypeByCode() {
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
