package com.belpost.apas.service;

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
import com.belpost.apas.model.common.Node;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.OfficeRepository;
import com.belpost.apas.service.util.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
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

    private final List<Long> GEN_1 = Arrays.asList(2L, 3L);
    private final List<Long> GEN_2 = Arrays.asList(4L, 5L, 6L, 7L);
    private final List<Long> GEN_3 =
        Arrays.asList(16L, 17L, 18L, 19L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L);
    private final List<Long> GEN_4 = Arrays.asList(20L, 21L);
    private final List<Long> ANCESTORS = Arrays.asList(1L, 2L, 3L,
        4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L, 21L);

    @Mock
    OfficeRepository officeRepository;

    @Mock
    OfficeTypeService officeTypeService;

    @Spy
    OfficeMapper officeMapper = new OfficeMapperImpl();

    @InjectMocks
    OfficeServiceImpl service;

    private Office entity;
    private OfficeModel model;
    private CustomObjectMapper customObjectMapper;

    @BeforeEach
    void setUp() {
        entity = createEntity(OFFICE_ID, OFFICE_CODE);
        entity.setParentId(PARENT_OFFICE_ID);
        entity.setOfficeTypeId(OFFICE_TYPE_ID);

        Office parent = createEntity(PARENT_OFFICE_ID, PARENT_OFFICE_CODE);
        parent.setOfficeTypeId(OFFICE_TYPE_ID);

        model = createModel();

        OfficeTypeModel type = mock(OfficeTypeModel.class);

        customObjectMapper = new CustomObjectMapper(new ObjectMapper());

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
        OfficeModel model = new OfficeModel();
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
        Assertions.assertThat(officeModel).isEqualTo(model);
        verify(officeRepository).findByCode(OFFICE_CODE);
        verify(officeRepository).findById(PARENT_OFFICE_ID);
        verify(officeMapper).mapToModel(entity);

    }

    @Test
    void shouldGetOfficeById() {
        //when
        OfficeModel officeModel = service.getById(OFFICE_ID);

        //then
        Assertions.assertThat(officeModel).isEqualTo(model);
        verify(officeRepository).findById(OFFICE_ID);
        verify(officeRepository).findById(PARENT_OFFICE_ID);
        verify(officeMapper).mapToModel(entity);

    }

    @Test
    void shouldGetAll() throws IOException {
        //given
        List<OfficeTypeModel> officeTypes = customObjectMapper
            .readListFromFile("src/test/resources/json/officeType/officeTypeAll.json", OfficeTypeModel.class);

        List<Office> offices = customObjectMapper
            .readListFromFile("src/test/resources/json/office/officeAll.json", Office.class);
        when(officeRepository.findAll()).thenReturn(offices);
        when(officeTypeService.getAll()).thenReturn(officeTypes);

        //when
        List<OfficeModel> officeModels = service.getAll();

        //then
        Assertions.assertThat(officeModels)
            .hasSize(21);
        verify(officeRepository).findAll();
        verify(officeTypeService).getAll();
        verify(officeMapper, times(21))
            .mapToModel(any(Office.class));

    }

    @Test
    void shouldGetNode() throws IOException {
        //given
        List<OfficeTypeModel> officeTypes = customObjectMapper
            .readListFromFile("src/test/resources/json/officeType/officeTypeAll.json", OfficeTypeModel.class);
        when(officeTypeService.getAll()).thenReturn(officeTypes);

        List<Office> office1gen = customObjectMapper
            .readListFromFile("src/test/resources/json/office/office1gen.json", Office.class);
        when(officeRepository.findAllByParentIdIn(PARENT_OFFICE_ID)).thenReturn(office1gen);

        List<Office> office2gen = customObjectMapper
            .readListFromFile("src/test/resources/json/office/office2gen.json", Office.class);
        when(officeRepository.findAllByParentIdIn(GEN_1.toArray(new Long[0]))).thenReturn(office2gen);

        List<Office> office3gen = customObjectMapper
            .readListFromFile("src/test/resources/json/office/office3gen.json", Office.class);
        when(officeRepository.findAllByParentIdIn(GEN_2.toArray(new Long[0])))
            .thenReturn(office3gen);

        List<Office> office4gen = customObjectMapper
            .readListFromFile("src/test/resources/json/office/office4gen.json", Office.class);
        when(officeRepository.findAllByParentIdIn(GEN_3.toArray(new Long[0])))
            .thenReturn(office4gen);

        when(officeRepository.findAllByParentIdIn(GEN_4.toArray(new Long[0])))
            .thenReturn(Collections.emptyList());

        List<Office> descendents = customObjectMapper
            .readListFromFile("src/test/resources/json/office/officeDescendents.json", Office.class);
        when(officeRepository.findAllByParentIdIn(ANCESTORS.toArray(new Long[0])))
            .thenReturn(descendents);

        //when
        Node<OfficeModel> node = service.getAsTree(PARENT_OFFICE_ID);
        List<OfficeModel> listFromNode = service.convertToList(node);

        //then
        Assertions.assertThat(listFromNode)
            .hasSize(21);
        Assertions.assertThat(node.getNodeElement().getCode()).isEqualTo(PARENT_OFFICE_CODE);

        verify(officeTypeService).getAll();
        verify(officeRepository, times(6))
            .findAllByParentIdIn(any());
        verify(officeMapper, times(21))
            .mapToModel(any(Office.class));
    }


}
