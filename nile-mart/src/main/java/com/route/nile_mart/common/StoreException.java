package com.route.nile_mart.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StoreException extends RuntimeException {
    private final int status;

    public StoreException(String message) {
        this(message, 400);
    }

    public StoreException(String message, Throwable cause) {
        super(message, cause);
        this.status = 400;
    }

    protected StoreException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int status() {
        return status;
    }
}