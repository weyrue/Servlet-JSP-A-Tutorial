package app10d.controller;

import app10a.form.ProductForm;
import app10a.model.Product;
import app10c.validator.ProductValidator;
import app10d.action.GetProductAction;
import app10d.action.SaveProductAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Controller3Servlet", urlPatterns = {
        "/jsp/app10d/product_input", "/jsp/app10d/product_save", "/jsp/app10d/product_list" })
public class Controller3Servlet extends HttpServlet {

    private static final long serialVersionUID = 679L;

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {

        String uri = request.getRequestURI();
        /*
         * uri is in this form: /contextName/resourceName,
         * for example: /app10a/product_input.
         * However, in the case of a default context, the
         * context name is empty, and uri has this form
         * /resourceName, e.g.: /product_input
         */
        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        String dispatchUrl = null;

        // no action class, there is nothing to be done
        if (action.equals("product_input")) dispatchUrl = "/jsp/app10d/ProductForm.jsp";
        else if (action.equals("product_save")) {
            // instantiate action class
            ProductForm productForm = new ProductForm();
            // populate action properties
            productForm.setName(
                    request.getParameter("name"));
            productForm.setDescription(
                    request.getParameter("description"));
            productForm.setPrice(request.getParameter("price"));

            // validate ProductForm
            ProductValidator productValidator = new ProductValidator();
            List<String> errors = productValidator.validate(productForm);
            if (errors.isEmpty()) {
                // create Product from ProductForm
                Product product = new Product();
                product.setName(productForm.getName());
                product.setDescription(productForm.getDescription());
                product.setPrice(Float.parseFloat(productForm.getPrice()));

                // no validation error, execute action method
                SaveProductAction saveProductAction = new SaveProductAction();
                saveProductAction.save(product);

                // store action in a scope variable for the view
                request.setAttribute("product", product);
                dispatchUrl = "/jsp/app10d/ProductDetails.jsp";
            } else {
                request.setAttribute("errors", errors);
                request.setAttribute("form", productForm);
                dispatchUrl = "/jsp/app10d/ProductForm.jsp";
            }
        } else if (action.equals("product_list") || action.isEmpty()) {
            GetProductAction getProductsAction = new GetProductAction();
            List<Product> products = getProductsAction.getProducts();
            request.setAttribute("products", products);
            dispatchUrl = "/jsp/app10d/ProductList.jsp";
        }

        // forward to a view
        if (dispatchUrl != null) {
            RequestDispatcher rd =
                    request.getRequestDispatcher(dispatchUrl);
            rd.forward(request, response);
        }
    }
}