package app10c.controller;

import app10a.action.SaveProductAction;
import app10a.form.ProductForm;
import app10a.model.Product;
import app10c.validator.ProductValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Controller2Servlet", urlPatterns = {"/jsp/app10c/product_input", "/jsp/app10c/product_save"})
public class Controller2Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        String dispatchUrl = null;

        if (action.equals("product_input")) {

        } else if (action.equals("product_save")) {
            ProductForm productForm = new ProductForm();
            productForm.setName(req.getParameter("name"));
            productForm.setDescription(req.getParameter("description"));
            productForm.setPrice(req.getParameter("price"));

            ProductValidator productValidator = new ProductValidator();
            List<String> errors = productValidator.validate(productForm);

            if (errors.isEmpty()) {
                Product product = new Product();
                product.setName(productForm.getName());
                product.setDescription(productForm.getDescription());
                product.setPrice(Float.parseFloat(productForm.getPrice()));

                SaveProductAction saveProductAction = new SaveProductAction();
                saveProductAction.save(product);

                req.setAttribute("product", product);

                dispatchUrl = "/jsp/app10c/ProductDetails.jsp";
            } else {
                req.setAttribute("errors", errors);
                req.setAttribute("form", productForm);
                dispatchUrl = "/jsp/app10c/ProductForm.jsp";
            }
        }

        if (dispatchUrl != null) {
            RequestDispatcher rd = req.getRequestDispatcher(dispatchUrl);
            rd.forward(req, resp);
        }
    }
}
