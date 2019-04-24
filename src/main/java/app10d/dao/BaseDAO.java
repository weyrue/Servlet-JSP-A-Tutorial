package app10d.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDAO implements DAO {
    @Override
    public Connection getConnection() throws DAOException {
        DataSource dataSource = DataSourceCache.getInstance().getDataSource();

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        }
    }

    protected void closeDBObjects(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }

    }
}
