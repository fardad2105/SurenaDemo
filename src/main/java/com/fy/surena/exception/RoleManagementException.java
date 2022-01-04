package com.fy.surena.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleManagementException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public RoleManagementException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
