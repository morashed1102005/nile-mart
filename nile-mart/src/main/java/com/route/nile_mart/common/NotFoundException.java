package com.route.nile_mart.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends StoreException {
    public NotFoundException(String message) {
        super(message, 404);
    }
}