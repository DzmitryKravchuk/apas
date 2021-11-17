package com.belpost.apas.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistance.entity.OfficeType;
import com.belpost.apas.utils.OfficeTypeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = OfficeTypeMapperImpl.class)
class OfficeTypeMapperTest {
    private static final String CODE = OfficeTypeModel.POST_OFFICE_CODE;

    @Autowired
    private OfficeTypeMapper mapper;

    @Test
    void shouldMapEntityToModel() {
        OfficeType entity = OfficeTypeUtils.entities.get(CODE);
        OfficeTypeModel expected = OfficeTypeUtils.models.get(CODE);

        OfficeTypeModel actual = mapper.mapToModel(entity);

        assertEquals(expected, actual);
    }

    @Test
    void shouldMapModelToEntity() {
        OfficeTypeModel model = OfficeTypeUtils.models.get(CODE);
        OfficeType expected = OfficeTypeUtils.entities.get(CODE);

        OfficeType actual = mapper.mapToEntity(model);

        assertEquals(expected, actual);
    }

}
