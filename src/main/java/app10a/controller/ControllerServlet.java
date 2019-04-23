package app10a.controller;

import app10a.action.SaveProductAction;
import app10a.form.ProductForm;
import app10a.model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/jsp/app10a/product_input", "/jsp/app10a/product_save"})
//@WebServlet(name = "ControllerServlet", urlPatterns = {"/"})
public class ControllerServlet extends HttpServlet {
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

        if (action.equals("product_input")) {

        } else if (action.equals("product_save")) {
            ProductForm productForm = new ProductForm();
            productForm.setName(req.getParameter("name"));
            productForm.setDescription(req.getParameter("description"));
            productForm.setPrice(req.getParameter("price"));

            Product product = new Product();
            product.setName(productForm.getName());
            product.setDescription(productForm.getDescription());
            try {
                product.setPrice(Float.parseFloat(productForm.getPrice()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            SaveProductAction saveProductAction = new SaveProductAction();
            saveProductAction.save(product);

            req.setAttribute("product", product);

            String dispatchUrl = null;
            if (action.equals("product_input")) {
                dispatchUrl = "/jsp/app10a/ProductForm.jsp";
            } else if (action.equals("product_save")) {
                dispatchUrl = "/jsp/app10a/ProductDetails.jsp";
            }
            if (dispatchUrl != null) {
                RequestDispatcher rd = req.getRequestDispatcher(dispatchUrl);
                rd.forward(req, resp);
            }
        }
    }
}
