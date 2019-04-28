package app10d.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceCache {
    private static Connection connection;

    public DataSourceCache() {
        String URL = "jdbc:mysql://localhost:3306/servlettest?useSSL=true";
        String username = "root";
        String password = "1234567890";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
