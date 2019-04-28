package app11a.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/jsp/app11a/singleUpload"})
@MultipartConfig
public class SingleUploadServlet extends HttpServlet {
    private String getFilename(Part part) {
        String contentDispositionHeader = part.getHeader("content-disposition");
        String[] elements = contentDispositionHeader.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("filename");
        String filename = getFilename(part);
        if (filename != null && !filename.isEmpty()) {
            part.write(getServletContext().getRealPath("WEB-INF") + "/" + filename);
        }

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.print("<br/>Uploaded file name: " + filename);
        writer.print("<br/>Size:  " + part.getSize());

        String author = req.getParameter("author");
        writer.print("<br/>Author: " + author);
    }
}
