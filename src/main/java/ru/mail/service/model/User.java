package ru.mail.service.model;


import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.service.jsonview.Views;

public class User {
    @JsonView(Views.Public.class)
    private String userEmail;
    @JsonView(Views.Public.class)
    private String userPassword;

    public User(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


}
