package app12a.filedownload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/jsp/app12a/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
//        if (userName != null && userName.equals("ken") && password != null && password.equals("secret")) {
        if (userName != null && password != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("loggedIn", Boolean.TRUE);
            resp.sendRedirect("download");
            return;
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/app12a/login.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
