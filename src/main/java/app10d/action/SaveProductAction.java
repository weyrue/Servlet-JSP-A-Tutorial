package app10d.action;

import app10a.model.Product;
import app10d.dao.DAOException;
import app10d.dao.DAOFactory;
import app10d.dao.ProductDAO;

public class SaveProductAction {
    public void save(Product product){
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            productDAO.insert(product);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
