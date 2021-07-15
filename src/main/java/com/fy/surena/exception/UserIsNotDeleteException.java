package com.fy.surena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User Can't Delete, Because this user is not exists")
public class UserIsNotDeleteException extends RuntimeException{

    public UserIsNotDeleteException(String message) {
        super(message);
    }
}
