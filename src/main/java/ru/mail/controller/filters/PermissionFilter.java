package ru.mail.controller.filters;


import ru.mail.service.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class PermissionFilter implements Filter {

    private static final String LOGIN_URL="/login";
    private static final String EXPLORER_URL="/explorer";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Permission filter is working");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //try to get user from session
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            //if user is not autificated - redirect to login page
            resp.sendRedirect(req.getContextPath() + LOGIN_URL);
        } else {
            String requestURL = req.getRequestURL().toString();
            // if user is autificated - checking where he want to go
            if (!requestURL.contains(LOGIN_URL)) {
                chain.doFilter(request, response);
            } else {
                // if user is autificated and want going to login page => redirect to explorer page
                resp.sendRedirect(req.getContextPath() + EXPLORER_URL);
            }
        }
    }

    @Override
    public void destroy() {
    }
}