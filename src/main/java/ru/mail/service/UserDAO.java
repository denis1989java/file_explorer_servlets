package ru.mail.service;

public interface UserDAO {
    boolean isValidUser(String userEmail, String password);
}
