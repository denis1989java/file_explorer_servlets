package ru.mail.controller.filters;

import javax.servlet.*;
import java.io.IOException;

public class RequestEncodeFilter implements Filter {
    private static final String ENCODING_TYPE="UTF-8";
    private static final String CONTENT_TYPE="text/html; charset=UTF-8";
    public RequestEncodeFilter() {
        System.out.println("Request response encoder filter object has been created");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        //Default
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("RequestEncode filter  is working");
        //setting request character encoding
        request.setCharacterEncoding(ENCODING_TYPE);
        chain.doFilter(request, response);
        //setting response content type
        response.setContentType(CONTENT_TYPE);
    }

    public void destroy() {
        //Default
    }
}