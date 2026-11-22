package com.route.nile_mart.controllerr;

import com.route.nile_mart.common.StoreException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StoreException.class)
    public String handleStoreException(StoreException ex, HttpServletResponse response, Model model) {
        response.setStatus(ex.status());
        model.addAttribute("status", ex.status());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}