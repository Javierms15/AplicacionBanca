package com.example.demo.config;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Profile("!test")
@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("sql7641808")
                .password("X4RnYMaRub")
                .url("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7641808")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();
    }

}
