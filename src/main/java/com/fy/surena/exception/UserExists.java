package com.fy.surena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with this username is Exists")
public class UserExists extends RuntimeException{

    public UserExists(String message) {
        super(message);
    }
}
