package com.belpost.apas.persistance.repository.lookup;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistance.entity.OfficeType;
import com.belpost.apas.persistance.repository.OfficeTypeRepository;
import com.belpost.apas.support.PersistenceTest;
import com.belpost.apas.utils.OfficeTypeUtils;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@PersistenceTest
class OfficeTypePersistenceTest {

    private static final String OFFICE_TYPE_CODE = OfficeTypeModel.ROOT_OFFICE_CODE;

    @Autowired
    OfficeTypeRepository repository;

    @BeforeEach
    @DataSet(value = {},
        cleanBefore = true, useSequenceFiltering = false)
    void setUp() {
    }

    @Test
    @ExpectedDataSet(value = "/dataset/officeType/officeType.yml")
    void shouldGetOfficeTypeByCode() {
        OfficeType ot = repository.findByCode(OfficeTypeModel.ROOT_OFFICE_CODE)
            .orElseThrow(() -> new ResourceNotFoundException("Failed to execute OfficeTypeRepositoryTest"));

        Assertions.assertThat(ot.getCode()).isEqualTo(OFFICE_TYPE_CODE);
        Assertions.assertThat(ot.getId()).isEqualTo(5L);
    }

    @Test
    void shouldGetAllOfficeTypes() {
        List<OfficeType> actual = repository.findAll();
        List<OfficeType> expected = new ArrayList<>(OfficeTypeUtils.entities.values());

        Assertions.assertThat(actual)
            .hasSize(5)
            .containsExactlyInAnyOrderElementsOf(expected);
    }

}
