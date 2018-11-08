package com.swapnil.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class BeanCreationConfig {
  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;
  @Value("${spring.datasource.url}")
  private String url;

  @Bean
  public DataSource getDataSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    return DataSourceBuilder
        .create()
        .driverClassName(driverClassName)
        .url(url)
        .build();
  }

  @Bean
  public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }
}
