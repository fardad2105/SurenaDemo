package com.fy.surena.config;

public class ControllerUtils {

    public static boolean isPasswordDifferent(String newPass, String oldpass) {
        return newPass != null && !newPass.equals(oldpass);
    }
}
