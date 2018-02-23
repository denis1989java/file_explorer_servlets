package ru.mail.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import ru.mail.service.UserDAO;
import ru.mail.service.impl.UserDAOImpl;
import ru.mail.service.jsonview.Views;
import ru.mail.service.model.AjaxResponseUser;
import ru.mail.service.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {


    private static final String EXPLORER_URL="/explorer";
    private static final String VALID_CODE="200";
    private static final String INVALID_CODE="204";
    private static final String VALID_MSG="valid user";
    private static final String INVALID_MSG="Credentials is not valid!";
    private static final String ENCODING_TYPE="UTF-8";
    private static final String CONTENT_TYPE="application/json";
    //getting access to service methods
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("coming to LoginServlet doGet");
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            resp.sendRedirect(req.getContextPath() +EXPLORER_URL);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }

    @Override
    @JsonView(Views.Public.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("coming to LoginServlet doPost");
        //getting json file from UI
        User data = new Gson().fromJson(req.getReader(), User.class);
        String login = data.getUserEmail();
        String password = data.getUserPassword();
        try {
            //checking is login and password is valid
            if (userDAO.isValidUser(login, password)) {
                User validUser = new User(login, password);
                //is user valid => setting user to session
                req.getSession().setAttribute("user", validUser);
                sendJSON(resp, VALID_MSG, VALID_CODE);
            } else {
                //sending json about invalid user
                sendJSON(resp, INVALID_MSG, INVALID_CODE);
            }
        } catch (IOException e) {
            System.out.println("ExceptionServlet during request");
            throw new IllegalStateException("Unknown error");
        }
    }

    private void sendJSON(HttpServletResponse resp, String msg, String code) throws IOException {
        //creating json file and setting settings
        AjaxResponseUser result = new AjaxResponseUser(msg, code);
        String json = new Gson().toJson(result);
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING_TYPE);
        resp.getWriter().write(json);
    }
}
