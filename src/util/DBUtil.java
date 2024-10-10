package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    public static Connection getDBConn() throws SQLException {
        try {
            Properties properties = loadProperties();

            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            String driver = properties.getProperty("jdbc.driver");

            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Unable to load database properties", e);
        }
    }

    private static Properties loadProperties() throws SQLException {
        try (FileInputStream fis = new FileInputStream(
                "src/util/db.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            return properties;
        } catch (IOException e) {
            throw new SQLException("Error loading database properties", e);
        }
    }
}
