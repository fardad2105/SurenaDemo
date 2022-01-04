package com.fy.surena.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionManagementException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public PermissionManagementException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
