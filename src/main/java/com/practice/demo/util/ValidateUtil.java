package com.practice.demo.util;

import java.util.regex.Pattern;

public class ValidateUtil {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static  final String ACCOUNT_REGEX = "^[a-zA-Z0-9]{5,15}$";
    public static  final String PASSWORD_REGEX = "^[a-zA-Z0-9]{5,30}$";


    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public static boolean isAccountCorrect(String account){
        return account != null && Pattern.compile(ACCOUNT_REGEX).matcher(account).matches();
    }

    public static boolean isPasswordCorrect(String password){
        return password != null && Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }
}
