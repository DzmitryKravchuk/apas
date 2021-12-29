package com.belpost.apas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.OfficeNodeRepository;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OfficeServiceImplTest {
    private static final String OFFICE_CODE = "231000";
    private static final String OFFICE_TYPE_CODE = "РeгУПС";
    private static final Long OFFICE_TYPE_ID = 3L;
    private static final String PARENT_OFFICE_CODE = "230000";
    private static final Long PARENT_OFFICE_ID = 1L;
    private static final Long OFFICE_ID = 2L;
    private static final Integer HIERARCHY_LVL = 1;

    @Mock
    //OfficeLookupRepository officeLookupRepository;
    OfficeNodeRepository officeLookupRepository;

    @Mock
    OfficeTypeServiceImpl officeTypeService;

    @Mock
    OfficeMapper officeMapper;

    @InjectMocks
    OfficeServiceImpl service;

    private static final Office entity = mock(Office.class);
    private static final OfficeModel model = mock(OfficeModel.class);
    private static final OfficeTypeModel type = mock(OfficeTypeModel.class);
    private static final Office parent = mock(Office.class);

    @BeforeEach
    void setUp() {
        lenient().when(officeLookupRepository.findByCode(OFFICE_CODE)).thenReturn(java.util.Optional.ofNullable(entity));
        lenient().when(officeLookupRepository.findById(OFFICE_ID)).thenReturn(java.util.Optional.ofNullable(entity));
        assert entity != null;
        lenient().when(entity.getOfficeTypeId()).thenReturn(OFFICE_TYPE_ID);
        lenient().when(officeTypeService.getById(OFFICE_TYPE_ID)).thenReturn(type);
        lenient().when(type.getCode()).thenReturn(OFFICE_TYPE_CODE);
        lenient().when(type.getHierarchyLvl()).thenReturn(HIERARCHY_LVL);
        lenient().when(entity.getParentId()).thenReturn(PARENT_OFFICE_ID);
        lenient().when(officeLookupRepository.findById(PARENT_OFFICE_ID)).thenReturn(java.util.Optional.ofNullable(parent));
        assert parent != null;
        lenient().when(parent.getCode()).thenReturn(PARENT_OFFICE_CODE);
        lenient().when(officeMapper.mapToModel(entity, OFFICE_TYPE_CODE, PARENT_OFFICE_CODE,HIERARCHY_LVL)).thenReturn(model);

    }

    @Test
    void shouldGetOfficeByCode() {
        //when
        OfficeModel officeModel = service.getByCode(OFFICE_CODE);

        //then
        assertEquals(model, officeModel);
        verify(officeLookupRepository).findByCode(OFFICE_CODE);
        verify(officeTypeService).getById(OFFICE_TYPE_ID);
        verify(officeLookupRepository).findById(PARENT_OFFICE_ID);
        verify(officeMapper).mapToModel(entity, OFFICE_TYPE_CODE, PARENT_OFFICE_CODE,HIERARCHY_LVL);

    }

    @Test
    void shouldGetOfficeById() {
        //when
        OfficeModel officeModel = service.getById(OFFICE_ID);

        //then
        assertEquals(model, officeModel);
        verify(officeLookupRepository).findById(OFFICE_ID);
        verify(officeTypeService).getById(OFFICE_TYPE_ID);
        verify(officeLookupRepository).findById(PARENT_OFFICE_ID);
        verify(officeMapper).mapToModel(entity, OFFICE_TYPE_CODE, PARENT_OFFICE_CODE, HIERARCHY_LVL);

    }

    @Test
    void shouldGetAll() throws IOException {
        //given
        CustomObjectMapper customObjectMapper = new CustomObjectMapper(new ObjectMapper());
        List<OfficeTypeModel> officeTypes = customObjectMapper
            .readListFromFile("src/test/resources/json/officeType/officeTypeAll.json", OfficeTypeModel.class);

        List<Office> offices = customObjectMapper
            .readListFromFile("src/test/resources/json/office/officeAll.json", Office.class);
        when(officeLookupRepository.findAll()).thenReturn(offices);
        when(officeTypeService.getAll()).thenReturn(officeTypes);

        //when
        List <OfficeModel> officeModels = service.getAll();

        //then
        assertEquals(21, officeModels.size());
        verify(officeLookupRepository).findAll();
        verify(officeTypeService).getAll();
        verify(officeMapper,times(20)).mapToModel(any(Office.class), any(String.class), any(String.class), any(Integer.class));
    }
}
