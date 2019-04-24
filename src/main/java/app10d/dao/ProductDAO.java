package app10d.dao;

import app10a.model.Product;

import java.util.List;

public interface ProductDAO extends DAO {
    List<Product> getProducts() throws DAOException;

    void insert(Product product) throws DAOException;
}
