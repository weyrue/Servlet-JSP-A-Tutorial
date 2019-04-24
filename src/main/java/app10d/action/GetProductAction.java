package app10d.action;

import app10a.model.Product;
import app10d.dao.DAOException;
import app10d.dao.DAOFactory;
import app10d.dao.ProductDAO;

import java.util.List;

public class GetProductAction {
    public List<Product> getProducts() {
        ProductDAO productDAO = DAOFactory.getProductDAO();
        List<Product> products = null;
        try {
            products = productDAO.getProducts();
        } catch (DAOException e) {
        }
        return products;
    }
}
