package app01a;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyServlet", urlPatterns = "/my")
public class MyServlet implements Servlet {
    private transient ServletConfig servletConfig;

    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
    }

    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        String servletName = servletConfig.getServletName();
        servletResponse.setContentType("text/html");
        PrintWriter writer = servletResponse.getWriter();
        writer.print("<html><head></head>" +
                "<body>Hello from " + servletName
                + "</body></html>");
    }

    public String getServletInfo() {
        return "My Servlet";
    }

    public void destroy() {

    }
}
