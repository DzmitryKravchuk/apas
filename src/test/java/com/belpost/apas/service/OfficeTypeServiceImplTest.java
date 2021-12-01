package com.belpost.apas.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.belpost.apas.exception.ResourceNotFoundException;
import com.belpost.apas.mapper.OfficeTypeMapper;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.persistence.entity.OfficeType;
import com.belpost.apas.persistence.repository.OfficeTypeRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OfficeTypeServiceImplTest {

    private static final String OFFICE_CODE = "POST_OFFICE";

    @Mock
    OfficeTypeRepository officeTypeRepository;

    @Mock
    OfficeTypeMapper officeTypeMapper;

    @InjectMocks
    OfficeTypeServiceImpl service;

    @Test
    void shouldGetOfficeTypeByCode() {
        //given
        OfficeType entity = mock(OfficeType.class);
        OfficeTypeModel model = mock(OfficeTypeModel.class);

        when(officeTypeRepository.findByCode(OFFICE_CODE)).thenReturn(java.util.Optional.ofNullable(entity));
        when(officeTypeMapper.mapToModel(entity)).thenReturn(model);

        //when
        OfficeTypeModel officeTypeModel = service.getByCode(OFFICE_CODE);

        //then
        assertEquals(model,officeTypeModel);
        verify(officeTypeRepository).findByCode(OFFICE_CODE);
        verify(officeTypeMapper).mapToModel(entity);

    }

    @Test
    void shouldGetResourceNotFoundException() {
        //given
        when(officeTypeRepository.findByCode(OFFICE_CODE)).thenReturn(Optional.empty());

        //when
        assertThrows(ResourceNotFoundException.class, () -> service.getByCode(OFFICE_CODE));

        //then
        verify(officeTypeRepository).findByCode(OFFICE_CODE);
    }

    @Test
    void shouldGetAllOfficeTypes() {
        //given
        when(officeTypeRepository.findAll()).thenReturn(Collections.singletonList(new OfficeType()));
        when(officeTypeMapper.mapToModel(any(OfficeType.class))).thenReturn(new OfficeTypeModel());

        //when
        List<OfficeTypeModel> l = service.findAll();

        //then
        verify(officeTypeRepository,times(1)).findAll();
        verify(officeTypeMapper,times(1)).mapToModel(any(OfficeType.class));
    }

}
