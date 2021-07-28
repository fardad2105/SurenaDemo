package com.fy.surena.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ControllerUtils {


    public boolean isPasswordDifferent(String newPass, String oldpass) {
        return newPass != null && !newPass.equals(oldpass);
    }
}
