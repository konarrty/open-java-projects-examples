package com.example.aston.conn;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {

    private static final DataSource dataSource = getDatasource();

    public static DataSource getDatasource() {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(Runtime.getRuntime().availableProcessors());
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/countries?useUnicode=true&characterEncoding=UTF-8");
        return new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
