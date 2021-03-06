package app02a.httpsession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingCartServlet extends HttpServlet {

    private static final String CART_ATTRIBUTE = "cart";

    private List<Product> products = new ArrayList<>();
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    @Override
    public void init() throws ServletException {
        products.add(new Product(1, "Bravo 32' HDTV", "Low-cost HDTV from renowned TV manufacturer", 159.95F));
        products.add(new Product(2, "Bravo BlueRay Player", "High quality stylish blueray player", 99.95F));
        products.add(new Product(3, "Bravo Stereo System", "5 speaker hifi system with ipod player", 129.95F));
        products.add(new Product(4, "Bravo iPod Player", "A iPod plug-in that can play multiple formats", 39.95F));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/products")) {
            sendProductList(resp);
        }
        if (uri.endsWith("/viewProductDetails")) {
            sendProductDetails(req, resp);
        }
        if (uri.endsWith("/viewCart")) {
            showCart(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = 0;
        int quantity = 0;

        try {
            productId = Integer.parseInt(req.getParameter("id"));
            quantity = Integer.parseInt(req.getParameter("quantity"));
        } catch (NumberFormatException e) {
        }

        Product product = getProduct(productId);
        if (product != null && quantity >= 0) {
            ShoppingItem shoppingItem = new ShoppingItem(product, quantity);
            HttpSession session = req.getSession();
            List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);

            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute(CART_ATTRIBUTE, cart);
            }
            cart.add(shoppingItem);
        }
        sendProductList(resp);
    }

    private void sendProductList(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html><head><title>Products</title></head>" +
                "<body><h2>Products</h2><ul>");
        for (Product product : products) {
            writer.println("<li>" + product.getName() + "(" +
                    currencyFormat.format(product.getPrice()) + ") (" +
                    "<a href='viewProductDetails?id=" + product.getId() + "'>Details</a>)");
        }
        writer.println("</ul>" +
                "<a href='viewCart'>View Cart</a>" +
                "</body></html>");
    }

    private Product getProduct(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    private void sendProductDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        int productId = 0;

        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Product product = getProduct(productId);

        if (product != null) {
            writer.println("<html><head><title>Product Details</title></head>" +
                    "<body><h2>Product Details</h2>" +
                    "<form method='post' action='addToCart'" +
                    "<input type='hidden' name='id' value='" + productId + "'/>" +
                    "<table><tr><td>Name:</td><td>" + product.getName() + "</td></tr>" +
                    "<tr><td>Description:</td><td>" + product.getDescription() + "</td></tr>" +
                    "<tr></tr><td><input name='quantity'/></td>" +
                    "<td><input type='submit' value='Buy'/></td></tr>" +
                    "<tr><td colspan='2'>" +
                    "<a href='products'>Product List</a>" +
                    "</td></tr></table></form></body>");
        } else {
            writer.println("No product found;");
        }

    }

    private void showCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html><head><title>Shopping Cart</title></head>" +
                "<body><a href='products'>" +
                "Product List</a>");

        HttpSession session = request.getSession();
        List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);

        if (cart != null) {
            writer.println("<table><tr><td style='width:150px'>Quantity</td>" +
                    "<td style = 'width=150px'>Product</td>" +
                    "<td style = 'width=150px'>Price</td>" +
                    "<td>Amount</td></tr>");

            double total = 0.0;

            for (ShoppingItem shoppingItem : cart) {
                Product product = shoppingItem.getProduct();
                int quantity = shoppingItem.getQuantity();
                if (quantity != 0) {
                    float price = product.getPrice();
                    writer.println("<tr><td>" + quantity + "</td>" +
                            "<td>" + product.getName() + "</td>" +
                            "<td>" + currencyFormat.format(price) + "</td>");

                    double subtotal = price * quantity;

                    writer.println("<td>" + currencyFormat.format(subtotal) + "</td>");
                    total += subtotal;
                    writer.println("</tr>");
                }
            }
            writer.println("<tr><td colspan='4' style = 'text-align:right'>Total: " + currencyFormat.format(total) + "</td></tr></table>");
        }
        writer.println("</table></body></html>");
    }
}
