package app10b.filter;

import app10a.action.SaveProductAction;
import app10a.form.ProductForm;
import app10a.model.Product;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "DispatcherFilter", urlPatterns = {"/jsp/app10a/*"})
public class DispatcherFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);

        if (action.equals("product_input")) {

        } else if (action.equals("product_save")) {
            ProductForm productForm = new ProductForm();
            productForm.setName(request.getParameter("name"));
            productForm.setDescription(request.getParameter("description"));
            productForm.setPrice(request.getParameter("price"));

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

            request.setAttribute("product", product);
        }

        String dispatchUrl = null;
        if (action.equals("product_input")) {
            dispatchUrl = "/jsp/app10a/ProductForm.jsp";
        } else if (action.equals("product_save")) {
            dispatchUrl = "/jsp/app10a/ProductDetails.jsp";
        }

        if (dispatchUrl != null) {
            RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
            rd.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
