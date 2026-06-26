package org.gym.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    public static final String DB_URL;
    public static final String DB_USERNAME;
    public static final String DB_PASSWORD;

    static {
        Properties properties = new Properties();
        try {
            InputStream inputStream = ConnectionManager.class.getClassLoader().
                    getResourceAsStream("application.properties");
            properties.load(inputStream);
            DB_URL = properties.getProperty("db_url");
            DB_USERNAME = properties.getProperty("db_username");
            DB_PASSWORD = properties.getProperty("db_password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}