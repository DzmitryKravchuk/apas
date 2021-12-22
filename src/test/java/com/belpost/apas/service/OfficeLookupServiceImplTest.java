package com.belpost.apas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OfficeLookupServiceImplTest {
    private static final String OFFICE_CODE = "231000";
    private static final String OFFICE_TYPE_CODE = "РeгУПС";
    private static final Long OFFICE_TYPE_ID = 3L;
    private static final String PARENT_OFFICE_CODE = "230000";
    private static final Long PARENT_OFFICE_ID = 1L;
    private static final Long OFFICE_ID = 2L;

    @Mock
    OfficeRepository officeRepository;

    @Mock
    OfficeTypeLookupServiceImpl officeTypeService;

    @Mock
    OfficeMapper officeMapper;

    @InjectMocks
    OfficeLookupServiceImpl service;

    @Test
    void shouldGetOfficeByCode() {
        //given
        Office entity = mock(Office.class);
        OfficeModel model = mock(OfficeModel.class);
        OfficeTypeModel type = mock(OfficeTypeModel.class);
        Office parent = mock(Office.class);

        when(officeRepository.findByCode(OFFICE_CODE)).thenReturn(java.util.Optional.ofNullable(entity));
        when(entity.getOfficeTypeId()).thenReturn(OFFICE_TYPE_ID);
        when(officeTypeService.getById(OFFICE_TYPE_ID)).thenReturn(type);
        when(type.getCode()).thenReturn(OFFICE_TYPE_CODE);
        when(entity.getParentOfficeId()).thenReturn(PARENT_OFFICE_ID);
        when(officeRepository.findById(PARENT_OFFICE_ID)).thenReturn(java.util.Optional.ofNullable(parent));
        when(parent.getCode()).thenReturn(PARENT_OFFICE_CODE);
        when(officeMapper.mapToModel(entity, OFFICE_TYPE_CODE, PARENT_OFFICE_CODE)).thenReturn(model);


        //when
        OfficeModel officeModel = service.getByCode(OFFICE_CODE);

        //then
        assertEquals(model, officeModel);
        verify(officeRepository).findByCode(OFFICE_CODE);
        verify(entity).getOfficeTypeId();
        verify(officeTypeService).getById(OFFICE_TYPE_ID);
        verify(type).getCode();
        verify(entity).getParentOfficeId();
        verify(officeRepository).findById(PARENT_OFFICE_ID);
        verify(parent).getCode();
        verify(officeMapper).mapToModel(entity, OFFICE_TYPE_CODE, PARENT_OFFICE_CODE);

    }

}
