package com.belpost.apas.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
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
    private static final Long OFFICE_ID = 8L;
    private static final String OFFICE_CODE = "231101";
    private static final Long OFFICE_TYPE_ID = 2L;

    private static final Long LOCAL_OFFICE_ID = 4L;
    private static final String LOCAL_OFFICE_CODE = "231100";

    private static final Long REGIONAL_OFFICE_ID = 2L;

    private static final Long ROOT_OFFICE_ID = 1L;
    private static final String ROOT_OFFICE_CODE = "230000";

    private final List<Long> GEN_1 = Arrays.asList(2L, 3L);
    private final List<Long> GEN_2 = Arrays.asList(4L, 5L, 6L, 7L);
    private final List<Long> GEN_3 =
        Arrays.asList(16L, 17L, 18L, 19L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L);
    private final List<Long> GEN_4 = Arrays.asList(20L, 21L);
    private final List<Long> ANCESTORS_1 = Arrays.asList(1L, 2L, 3L,
        4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L, 21L);
    private final List<Long> ANCESTORS_2 = Arrays.asList(4L, 20L, 21L, 8L, 9L, 10L);

    @Mock
    OfficeRepository officeRepository;

    @Mock
    OfficeTypeService officeTypeService;

    @Spy
    OfficeMapper officeMapper = new OfficeMapperImpl();

    @InjectMocks
    OfficeServiceImpl service;

    Office rootOffice;
    Office regionalOffice;
    Office localOffice;
    private Office postOffice;
    private OfficeModel postOfficeModel;
    private CustomObjectMapper customObjectMapper;

    @BeforeEach
    void setUp() throws IOException {
        customObjectMapper = new CustomObjectMapper(new ObjectMapper());

        rootOffice = customObjectMapper.readFromFile("src/test/resources/json/office/rootOffice.json", Office.class);
        regionalOffice = customObjectMapper.readFromFile("src/test/resources/json/office/regionalOffice.json", Office.class);
        localOffice = customObjectMapper.readFromFile("src/test/resources/json/office/localOffice.json", Office.class);
        postOffice = customObjectMapper.readFromFile("src/test/resources/json/office/postOffice.json", Office.class);
        postOfficeModel = customObjectMapper.readFromFile("src/test/resources/json/office/postOfficeModel.json", OfficeModel.class);

        List<OfficeTypeModel> officeTypes = customObjectMapper
            .readListFromFile("src/test/resources/json/officeType/officeTypeAll.json", OfficeTypeModel.class);

        lenient().when(officeTypeService.getAll()).thenReturn(officeTypes);

        lenient().when(officeRepository.findByCode(OFFICE_CODE))
            .thenReturn(java.util.Optional.ofNullable(postOffice));
        lenient().when(officeRepository.findById(OFFICE_ID)).thenReturn(java.util.Optional.ofNullable(postOffice));

        lenient().when(officeRepository.findById(ROOT_OFFICE_ID))
            .thenReturn(java.util.Optional.of(rootOffice));

        lenient().when(officeRepository.findById(REGIONAL_OFFICE_ID))
            .thenReturn(java.util.Optional.of(regionalOffice));

        lenient().when(officeRepository.findById(LOCAL_OFFICE_ID))
            .thenReturn(java.util.Optional.of(localOffice));
    }


    @Test
    void shouldGetOfficeByCode() {
        //when
        OfficeModel officeModel = service.getByCode(OFFICE_CODE);

        //then
        Assertions.assertThat(officeModel).isEqualTo(postOfficeModel);
        verify(officeRepository).findByCode(OFFICE_CODE);
        verify(officeRepository).findById(LOCAL_OFFICE_ID);
        verify(officeMapper).mapToModel(postOffice);

    }

    @Test
    void shouldGetParentById() {
        //when
        OfficeModel officeModel = service.getNodeById(OFFICE_ID);

        //then
        Assertions.assertThat(officeModel.getCode()).isEqualTo(OFFICE_CODE);
        Assertions.assertThat(officeModel.getParentId()).isEqualTo(LOCAL_OFFICE_ID);
        Assertions.assertThat(officeModel.getOfficeTypeId()).isEqualTo(OFFICE_TYPE_ID);
        Assertions.assertThat(officeModel.getParentOfficeCode()).isNull();
        Assertions.assertThat(officeModel.getOfficeTypeCode()).isNull();
        verify(officeRepository).findById(OFFICE_ID);
        verify(officeMapper).mapToModel(postOffice);

    }

    @Test
    void shouldGetOfficeById() {
        //when
        OfficeModel officeModel = service.getById(OFFICE_ID);

        //then
        Assertions.assertThat(officeModel).isEqualTo(postOfficeModel);
        verify(officeRepository).findById(OFFICE_ID);
        verify(officeRepository).findById(LOCAL_OFFICE_ID);
        verify(officeMapper).mapToModel(postOffice);

    }

    @Test
    void shouldGetNodeFromRoot() throws IOException {
        //given
        List<Office> office1gen = customObjectMapper
            .readListFromFile("src/test/resources/json/office/office1gen.json", Office.class);
        when(officeRepository.findAllByParentIdIn(ROOT_OFFICE_ID)).thenReturn(office1gen);

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
            .readListFromFile("src/test/resources/json/office/descendentsAll.json", Office.class);
        when(officeRepository.findAllByParentIdIn(ANCESTORS_1.toArray(new Long[0])))
            .thenReturn(descendents);

        //when
        Node<OfficeModel> node = service.getAsTree(ROOT_OFFICE_ID);
        List<OfficeModel> listFromNode = service.convertToList(node);

        //then
        Assertions.assertThat(listFromNode)
            .hasSize(21);
        Assertions.assertThat(node.getNodeElement().getCode()).isEqualTo(ROOT_OFFICE_CODE);

        verify(officeTypeService).getAll();
        verify(officeRepository, times(6))
            .findAllByParentIdIn(any());
        verify(officeMapper, times(21))
            .mapToModel(any(Office.class));
    }

    @Test
    void shouldGetNode() throws IOException {
        //given
        List<Office> office2gen = customObjectMapper
            .readListFromFile("src/test/resources/json/office/officeForLocalId4.json", Office.class);
        when(officeRepository.findAllByParentIdIn(LOCAL_OFFICE_ID)).thenReturn(office2gen);

        List<Office> office3gen = customObjectMapper
            .readListFromFile("src/test/resources/json/office/office4gen.json", Office.class);
        when(officeRepository.findAllByParentIdIn(8L, 9L, 10L))
            .thenReturn(office3gen);

        when(officeRepository.findAllByParentIdIn(GEN_4.toArray(new Long[0])))
            .thenReturn(Collections.emptyList());

        List<Office> descendents = customObjectMapper
            .readListFromFile("src/test/resources/json/office/descendentsOffice.json", Office.class);
        when(officeRepository.findAllByParentIdIn(ANCESTORS_2.toArray(new Long[0])))
            .thenReturn(descendents);

        //when
        Node<OfficeModel> node = service.getAsTree(LOCAL_OFFICE_ID);
        List<OfficeModel> listFromNode = service.convertToList(node);

        //then
        Assertions.assertThat(listFromNode)
            .hasSize(6);
        Assertions.assertThat(node.getNodeElement().getCode()).isEqualTo(LOCAL_OFFICE_CODE);

        verify(officeTypeService).getAll();
        verify(officeRepository, times(4))
            .findAllByParentIdIn(any());
    }

    @Test
    void shouldGetBranch() throws IOException {
        //given
        List<Office> office2gen = customObjectMapper
            .readListFromFile("src/test/resources/json/office/officeForLocalId4.json", Office.class);
        when(officeRepository.findAllByParentIdIn(LOCAL_OFFICE_ID)).thenReturn(office2gen);
        Node<OfficeModel> branchExpected = customObjectMapper
            .readNodeFromFile("src/test/resources/json/office/branchLocalOfficeNode.json", OfficeModel.class);

        //when
        Node<OfficeModel> node = service.getAsBranch(ROOT_OFFICE_ID, LOCAL_OFFICE_ID);

        Assertions.assertThat(node).isEqualTo(branchExpected);
    }

}
