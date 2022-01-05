package com.belpost.apas.service;

import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.common.Node;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest
@Sql({"classpath:dataset/office/office_data.sql"})
class DeleteMeIntegrationTest {
    private static final Long OFFICE_ID = 1L;

    @Autowired
    private OfficeService service;

    @Test
    void shouldGetByCode() {
        Node<OfficeModel> n = service.getAsTree(OFFICE_ID);
        service.printTree(n,"-");
    }
}