package app02a;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CookieInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        StringBuilder styles = new StringBuilder();
        styles.append(".title{");

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();

                if (name.equals("titleFontSize")) {
                    styles.append("font-size:" + value + ";");
                } else if (name.equals("titleFontWeight")) {
                    styles.append("font-weight:" + value + ";");
                } else if (name.equals("titleFontStyle")) {
                    styles.append("font-style:" + value + ";");
                }
            }
        }
        styles.append("}");

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.print("<html><head><title>Cookie Info</title>" +
                "<style>" + styles.toString() + "</style>" +
                "</head><body>" + PreferenceServlet.MENU +
                "<div class='title'>" +
                "Session Management with cookies:</div><div>");
        if (cookies == null) {
            writer.print("No cookie in this HTTP response.");
        } else {
            writer.println("<br/>Cookies in this HTTP response:");
            for (Cookie cookie : cookies) {
                writer.println("<br/>" + cookie.getName() + ":" + cookie.getValue());
            }
        }

        writer.println("</div></body></html>");
    }
}
