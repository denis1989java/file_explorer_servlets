package ru.mail.service.impl;

import ru.mail.service.UserDAO;
import ru.mail.service.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private List<User> users;

    public UserDAOImpl() {
        iniDataForTesting();
    }

    @Override
    public boolean isValidUser(String userEmail, String password) {
        //checking is login and password valid
        boolean isValid = false;
        for (User user : users) {
            if (user.getUserEmail().equals(userEmail.toLowerCase())) {
                if (user.getUserPassword().equals(password)) {
                    isValid = true;
                    break;
                }
            }
        }
        System.out.println("user isValid: "+ isValid);
        return isValid;
    }

    //creating default users
    private void iniDataForTesting() {
        users = new ArrayList<User>();
        users.add(new User("denis@mail.ru", "1234"));
        users.add(new User("vadim@mail.ru", "1234"));
        users.add(new User("leha@mail.ru", "1234"));
        users.add(new User("timoha@mail.ru", "1234"));
    }
}
