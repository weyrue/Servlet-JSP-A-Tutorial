package app12a.filedownload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet(urlPatterns = {"/jsp/app12a/download"})
public class FiledownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null || session.getAttribute("loggedIn") == null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/app12a/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        String dataDirectory = req.getServletContext().getRealPath("/WEB-INF/data");
        File file = new File(dataDirectory, "proxy.txt");
        if (file.exists()) {
            resp.setContentType("application/pdf");
            resp.addHeader("Content-Disposition", "attachment;filename=secret.pdf");
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = resp.getOutputStream();
                int i = bis.read(buffer);
                while (i != 1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }
}
