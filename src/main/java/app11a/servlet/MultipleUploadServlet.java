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
import java.util.Collection;

@WebServlet(urlPatterns = {"/jsp/app11a/multipleUploads"})
@MultipartConfig
public class MultipleUploadServlet extends HttpServlet {
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
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        Collection<Part> parts = req.getParts();
        for (Part part : parts) {
            if (part.getContentType() != null) {
                String filename = getFilename(part);
                if (filename != null && !filename.isEmpty()) {
                    part.write(getServletContext().getRealPath("WEB-INF") + "/" + filename);
                    writer.print("<br/>Uploaded file name: " + filename);
                    writer.print("<br/>Size:  " + part.getSize());
                }
            } else {
                String partName = part.getName();
                String fileValue = req.getParameter(partName);
                writer.print("<br/>" + partName + ": " + fileValue);
            }
        }

    }
}
