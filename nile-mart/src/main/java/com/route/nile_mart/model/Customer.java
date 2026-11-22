package com.route.nile_mart.model;

import com.route.nile_mart.common.StoreException;

public record Customer(String fullName, String phone, String email) {

    public Customer {
        if (fullName == null || fullName.isBlank()) {
            throw new StoreException("Customer full name is required");
        }
        fullName = fullName.trim();

        if (phone == null || !phone.trim().matches("01[0125][0-9]{8}")) {
            throw new StoreException("Phone number must be 11 digits starting with 010, 011, 012, or 015");
        }
        phone = phone.trim();

        if (email == null || email.isBlank()) {
            email = null;
        } else {
            email = email.trim();
            if (!email.contains("@")) {
                throw new StoreException("Email address must contain @");
            }
        }
    }
}