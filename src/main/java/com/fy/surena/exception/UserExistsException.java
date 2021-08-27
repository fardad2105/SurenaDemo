package com.fy.surena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserExistsException extends UserManagerException{
    public UserExistsException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
