package app09a;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "ImageProtectorFilter", urlPatterns = {"*.png", "*.jpg", "*.gif"})
public class ImageProtectorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("ImageProtectorFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String referer = httpServletRequest.getHeader("referer");
        System.out.println("referer: " + referer);
        if (referer != null) {
            chain.doFilter(request, response);
        } else {
            throw new ServletException("Image not available");
        }
    }

    @Override
    public void destroy() {

    }
}
