package com.example.tweetjdbc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "com.example.tweetjdbc.repository")
public class TestDbConfiguration {
    private final String SCHEMA_FILE = "classpath:schema.sql";
    private final String DATA_FILE = "classpath:data.sql";

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");

        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, applicationContext.getResource(SCHEMA_FILE));
            ScriptUtils.executeSqlScript(conn, applicationContext.getResource(DATA_FILE));
        }

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
