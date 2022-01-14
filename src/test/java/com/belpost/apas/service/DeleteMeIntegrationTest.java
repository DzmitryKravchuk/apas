package com.belpost.apas.service;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.common.Node;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.service.common.NodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest
@Sql({"classpath:dataset/office/office_data.sql"})
class DeleteMeIntegrationTest {
    private static final Long OFFICE_ID = 4L;

    @Autowired
    private NodeServiceImpl <Office, OfficeModel> service;

    @Test
    void shouldGetByCode() {
        Node<OfficeModel> n = service.getAsBranch(1L,OFFICE_ID);
        service.printTree(n,"-");
    }
}
