package com.belpost.apas.persistence.repository;

import com.github.database.rider.core.util.EntityManagerProvider;
import org.junit.jupiter.api.BeforeAll;

class OfficeRepositoryTest {

    @BeforeAll
    public static void initDB(){
        //trigger db creation
        EntityManagerProvider.instance("rules-it");
    }
}