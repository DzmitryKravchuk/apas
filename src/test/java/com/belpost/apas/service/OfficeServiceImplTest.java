package com.belpost.apas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.mapper.OfficeMapperImpl;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.OfficeRepository;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OfficeServiceImplTest {
    private static final Long OFFICE_ID = 2L;
    private static final String OFFICE_CODE = "231000";

    private static final Long PARENT_OFFICE_ID = 1L;
    private static final String PARENT_OFFICE_CODE = "230000";

    private static final Long OFFICE_TYPE_ID = 1L;
    private static final String OFFICE_TYPE_CODE = "any code";
    private static final Integer HIERARCHY_LVL = 1;

    @Mock
    OfficeRepository officeRepository;

    @Mock
    OfficeTypeServiceImpl officeTypeService;

    @Spy
    OfficeMapper officeMapper = new OfficeMapperImpl();

    @InjectMocks
    OfficeServiceImpl service;

    private Office entity;
    private OfficeModel model;

    @BeforeEach
    void setUp() {
        entity = createEntity(OFFICE_ID, OFFICE_CODE);
        entity.setParentId(PARENT_OFFICE_ID);
        entity.setOfficeTypeId(OFFICE_TYPE_ID);

        Office parent = createEntity(PARENT_OFFICE_ID, PARENT_OFFICE_CODE);

        model = createModel();

        OfficeTypeModel type = mock(OfficeTypeModel.class);

        lenient().when(officeTypeService.getById(OFFICE_TYPE_ID)).thenReturn(type);
        lenient().when(type.getCode()).thenReturn(OFFICE_TYPE_CODE);
        lenient().when(type.getHierarchyLvl()).thenReturn(HIERARCHY_LVL);
        lenient().when(officeRepository.findByCode(OFFICE_CODE))
            .thenReturn(java.util.Optional.ofNullable(entity));
        lenient().when(officeRepository.findById(OFFICE_ID)).thenReturn(java.util.Optional.ofNullable(entity));

        lenient().when(officeRepository.findById(PARENT_OFFICE_ID))
            .thenReturn(java.util.Optional.of(parent));
    }

    private OfficeModel createModel() {
        OfficeModel model =  new OfficeModel();
        model.setCode(OFFICE_CODE);
        model.setId(OFFICE_ID);
        model.setOfficeTypeId(OFFICE_TYPE_ID);
        model.setHierarchyLvl(HIERARCHY_LVL);
        model.setOfficeTypeCode(OFFICE_TYPE_CODE);
        model.setParentOfficeCode(PARENT_OFFICE_CODE);

        return model;
    }

    private Office createEntity(Long id, String code) {
        Office entity = new Office();
        entity.setCode(code);
        entity.setId(id);

        return entity;
    }

    @Test
    void shouldGetOfficeByCode() {
        //when
        OfficeModel officeModel = service.getByCode(OFFICE_CODE);

        //then
        assertEquals(model, officeModel);
        verify(officeRepository).findByCode(OFFICE_CODE);
        verify(officeRepository).findById(PARENT_OFFICE_ID);
        verify(officeMapper).mapToModel(entity);

    }

    @Test
    void shouldGetOfficeById() {
        //when
        OfficeModel officeModel = service.getById(OFFICE_ID);

        //then
        assertEquals(model, officeModel);
        verify(officeRepository).findById(OFFICE_ID);
        verify(officeRepository).findById(PARENT_OFFICE_ID);
        verify(officeMapper).mapToModel(entity);

    }

    @Test
    void shouldGetAll() throws IOException {
        //given
        CustomObjectMapper customObjectMapper = new CustomObjectMapper(new ObjectMapper());
        List<OfficeTypeModel> officeTypes = customObjectMapper
            .readListFromFile("src/test/resources/json/officeType/officeTypeAll.json", OfficeTypeModel.class);

        List<Office> offices = customObjectMapper
            .readListFromFile("src/test/resources/json/office/officeAll.json", Office.class);
        when(officeRepository.findAll()).thenReturn(offices);
        when(officeTypeService.getAll()).thenReturn(officeTypes);

        //when
        List<OfficeModel> officeModels = service.getAll();

        //then
        assertEquals(21, officeModels.size());
        verify(officeRepository).findAll();
        verify(officeTypeService).getAll();
        verify(officeMapper, times(21))
            .mapToModel(any(Office.class));

    }

}
