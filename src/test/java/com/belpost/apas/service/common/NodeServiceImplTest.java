package com.belpost.apas.service.common;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.mapper.OfficeMapperImpl;
import com.belpost.apas.mapper.OfficeTypeMapper;
import com.belpost.apas.mapper.OfficeTypeMapperImpl;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.AbstractPersistenceTest;
import com.belpost.apas.service.OfficeServiceImpl;
import com.belpost.apas.service.OfficeTypeServiceImpl;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:dataset/office/office_data.sql"})
class NodeServiceImplTest extends AbstractPersistenceTest {

    private OfficeModel rootModel;

    private OfficeTypeServiceImpl officeTypeService;
    private OfficeTypeMapper officeTypeMapper;

    private NodeServiceImpl<Office, OfficeModel> service;
    private OfficeMapper mapper;

    @BeforeEach
    void setUp() throws IOException {
        officeTypeMapper = new OfficeTypeMapperImpl();
        officeTypeService = new OfficeTypeServiceImpl(officeTypeRepository, officeTypeMapper);

        mapper = new OfficeMapperImpl();
        service = new OfficeServiceImpl(officeRepository, mapper, officeTypeService);

        rootModel = customObjectMapper
            .readFromFile("src/test/resources/json/office/rootOfficeModel.json", OfficeModel.class);
    }

    @Test
    void shouldPrintTree (){

    }

}
