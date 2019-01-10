package app02a;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CookieClassServlet extends HttpServlet {
    private String[] methods = {"clone", "getComment", "getDomain",
            "getMaxAge", "getName", "getPath",
            "getSecure", "getValue", "getVersion",
            "isHttpOnly", "setComment", "setDomain",
            "setHttpOnly", "setMaxAge", "setPath",
            "setSecure", "setValue", "setVersion"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie maxRecordsCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("maxRecords")) {
                    maxRecordsCookie = cookie;
                    break;
                }
            }
        }

        int maxRecords = 5;
        if (maxRecordsCookie != null) {
            try {
                maxRecords = Integer.parseInt(maxRecordsCookie.getValue());
            } catch (NumberFormatException e) {
            }
        }

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.print("<html><head>" +
                "<title>Cookie Class</title>" +
                "</head><body>" + PreferenceServlet.MENU +
                "<div>Here are some of the methods in javax.servlet.http.Cookie" +
                "<ul>");

        for (int i = 0; i < maxRecords; i++) {
            writer.print("<li>" + methods[i] + "</li>");
        }
        writer.print("</ul></div></body></html>");
    }
}
