package com.practice.demo.util;

import java.util.regex.Pattern;

public class ValidateUtil {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }
}
