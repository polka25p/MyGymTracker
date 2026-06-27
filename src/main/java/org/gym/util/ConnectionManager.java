package org.gym.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class responsible for managing database connections.
 * <p>
 * This class loads database configuration properties (URL, username, and password)
 * from an {@code application.properties} file located in the classpath during
 * its initialization. It provides a centralized way to obtain a {@link Connection}
 * to a PostgreSQL database.
 * </p>
 *
 * @author Polina
 * @version 1.0
 */

public class ConnectionManager {
    /**
     * The database URL used to establish a connection.
     */
    public static final String DB_URL;

    /**
     * The database username used for authentication.
     */
    public static final String DB_USERNAME;

    /**
     * The database password used for authentication.
     */
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

    /**
     * Establishes and returns a connection to the configured PostgreSQL database.
     * <p>
     * This method dynamically loads the PostgreSQL JDBC driver class
     * ({@code org.postgresql.Driver}) and then delegates the connection creation
     * to {@link DriverManager}.
     * </p>
     *
     * @return a {@link Connection} object linked to the database.
     * @throws SQLException if the PostgreSQL driver cannot be found,
     * or if a database access error occurs.
     */

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}