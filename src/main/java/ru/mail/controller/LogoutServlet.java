package ru.mail.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("coming to LogoutServlet doGet");
        //set null to user for session and redirect to login page
        req.getSession().setAttribute("user", null);
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
