package com.polsl.yachtclubmanager.utils;

public class EmailUtils {

    public static String getEmailMessage(String token) {
        return "Verification token: " + token;
    }

    public static String getResetPasswordMessage(String token) {
        return "Reset password token: " + token;
    }
}
