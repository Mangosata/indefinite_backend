package com.overwall.demo.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.LogRecord;

@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class GlobalFilter implements Filter {

    public GlobalFilter() {
        System.out.println("init reqFilter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        if (requestURI.contains("my")) {
            System.out.println("成功啦。。。, 请求URI是:" + requestURI);
        }
        filterChain.doFilter(request, servletResponse);
    }
}
