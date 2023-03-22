package com.challengeey.cruduserlogin.controller.utils;

public class Validators {

    public static boolean validateEmail(String email) {
        String expresionRegular = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(expresionRegular);
    }

    public static boolean validatePassword(String password) {
        String patron = "^(?=.*[A-Z])(?=.*\\d{2})(?=.*[a-z]).{8,}$";
        return password.matches(patron);
    }
}
