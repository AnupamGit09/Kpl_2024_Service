package com.kpl.registration.config.configMultiDB;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    @Bean(name = "postgresDataSource")
    @Primary

    // Map properties from application yml to each data source

    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSource postgresDataSource(){
        return DataSourceBuilder.create().build();
    }
}
