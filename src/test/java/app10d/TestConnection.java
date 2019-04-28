package app10d;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
//        DataSourceCache.getInstance().getDataSource();
        String URL = "jdbc:mysql://localhost:3306/servlettest?useSSL=true";
        String username = "root";
        String password = "1234567890";



        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
