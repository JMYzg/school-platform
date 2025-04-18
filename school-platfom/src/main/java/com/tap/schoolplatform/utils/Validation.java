package com.tap.schoolplatfom.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean ofEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";
        return isMatch(regex, email);
    }

    public static boolean ofName(String name) {
        String regex = "^[a-zA-Z][a-zA-Z\\s'-]{0,20}[a-zA-Z]$";
        return isMatch(regex, name);
    }

    public static boolean ofPhone(String phone) {
        String regex = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        return isMatch(regex, phone);
    }

    private static boolean isMatch(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
