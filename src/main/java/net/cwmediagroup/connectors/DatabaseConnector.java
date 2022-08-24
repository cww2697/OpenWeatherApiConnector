package net.cwmediagroup.connectors;

import net.cwmediagroup.services.EnvService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private final String dbUrl = EnvService.getEnvValue("DB_URL");
    private final String dbUser = EnvService.getEnvValue("DB_USER");
    private final String dbPassword = EnvService.getEnvValue("DB_PASS");
    private final String maxPool = EnvService.getEnvValue("DB_MAX_POOL");
    private final String dbDriver = EnvService.getEnvValue("DB_DRIVER");

    private Connection connection;
    private Properties properties;

    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", this.dbUser);
            properties.setProperty("password", this.dbPassword);
            properties.setProperty("MaxPooledStatements", this.maxPool);
        }
        return properties;
    }

    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(this.dbDriver);
                connection = DriverManager.getConnection(this.dbUrl, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
