package app08a;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

//@WebListener
public class PerfStatListener implements ServletRequestListener {
    // 统计点击次数
    private int clickCount = 0;

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        Long start = (Long) servletRequest.getAttribute("start");
        Long end = System.nanoTime();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String uri = httpServletRequest.getRequestURI();
        System.out.println("time taken to execute " + uri + ": " + ((end - start) / 1000000) + "microseconds");
        System.out.println("clickCount: " + clickCount);
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        clickCount++;
        sre.getServletRequest().setAttribute("start", System.nanoTime());
    }
}
