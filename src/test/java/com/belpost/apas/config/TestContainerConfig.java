package com.belpost.apas.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfig {
  private static final String IMAGE_VERSION = "postgres:12.7";

  @Bean(initMethod = "start", destroyMethod = "stop")
  public JdbcDatabaseContainer<?> pgDbContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse(IMAGE_VERSION))
        .withUsername("test")
        .withPassword("test")
        .withDatabaseName("apas_test");
  }

  @Bean
  public DataSource dataSource(JdbcDatabaseContainer<?> jdbcDatabaseContainer) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(jdbcDatabaseContainer.getJdbcUrl());
    hikariConfig.setUsername(jdbcDatabaseContainer.getUsername());
    hikariConfig.setPassword(jdbcDatabaseContainer.getPassword());

    return new HikariDataSource(hikariConfig);
  }

}
