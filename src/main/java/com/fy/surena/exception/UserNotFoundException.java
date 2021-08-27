package com.fy.surena.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserManagerException{
    public UserNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
