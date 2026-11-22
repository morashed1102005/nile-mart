package com.route.nile_mart.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OutOfStockException extends StoreException {
    public OutOfStockException(String message) {
        super(message, 409);
    }
}