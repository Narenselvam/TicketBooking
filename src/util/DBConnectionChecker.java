package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionChecker {
    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getDBConn();

            System.out.println("Successfully connected to the database!");

            // Test the connection by running a simple SQL query
            String sql = "SELECT Count(*) FROM event";
            try (Statement stmt = conn.createStatement();
                 java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    System.out.println("Query successful. Result: " + rs.getString(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
