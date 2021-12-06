package com.springboot.prac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 讀取自定義的 h2 schema
 */
@Profile("h2")
@Configuration
public class TestDataSourceConfig {

    @Value("${spring.test-datasource.platform}")
    private String platform;

    @Autowired
    ResourceLoader resourceLoader;

    @Bean("testDatasource")
    @ConfigurationProperties(prefix = "spring.test-datasource")
    public DataSource testDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier("testDatasource") DataSource dataSource){
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(resourceLoader.getResource("classpath:/testdata/schema-"+ platform +".sql"));
        resourceDatabasePopulator.addScript(resourceLoader.getResource("classpath:/testdata/data-"+ platform +".sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}
