package com.swapnil.derbyspringconnector.demo;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BeanCreationConfig {
  @Bean
  public DataSource getDataSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    return DataSourceBuilder
        .create()
        .driverClassName("org.apache.derby.jdbc.ClientDriver")
        .url("jdbc:derby://localhost:1527/db")
        .build();
  }
}
