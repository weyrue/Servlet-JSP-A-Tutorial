package app10d.dao;

import app10a.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductDAOImpl extends BaseDAO implements ProductDAO {
    private static final String GET_PRODUCTS_SQL = "SELECT name, description, price FROM products";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (name, description, price) VALUES (?, ?, ?)";

    @Override
    public List<Product> getProducts() throws DAOException {
        List<Product> products = new LinkedList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_PRODUCTS_SQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DAOException("Error getting products. " + e.getMessage());
        } finally {
            closeDBObjects(resultSet, preparedStatement, connection);
        }

        return products;
    }

    @Override
    public void insert(Product product) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error adding product. " + e.getMessage());
        } finally {
            closeDBObjects(null, preparedStatement, connection);
        }

    }
}
