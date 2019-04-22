package app09a;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebFilter(filterName = "LoggingFilter", urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "logFileName", value = "log.txt"),
                @WebInitParam(name = "prefix", value = "URI: ")})
public class LoggingFilter implements Filter {
    private PrintWriter logger;
    private String prefix;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("LoggingFilter.doFilter()");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        logger.println(new Date() + " " + prefix + httpServletRequest.getRequestURI());
        logger.flush();
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        prefix = filterConfig.getInitParameter("prefix");
        String logFileName = filterConfig.getInitParameter("logFileName");
        String appPath = filterConfig.getServletContext().getRealPath("/");
        System.out.println("logFileName: " + logFileName);
        System.out.println("logFilePath: " + appPath + "/" + logFileName);

        try {
            logger = new PrintWriter(new File(appPath, logFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroying filter");
        if (logger != null) {
            logger.close();
        }
    }
}
