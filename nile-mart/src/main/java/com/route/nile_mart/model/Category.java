package com.route.nile_mart.model;

import lombok.Getter;

@Getter
public enum Category {
    ELECTRONICS ("Electronics"),
    FASHION ("Fashion"),
    HOME_KITCHEN ("Home & Kitchen"),
    BEAUTY ("Beauty"),
    BOOKS ("Books");

    private final String label;

    Category(String label) {
        this.label = label;
    }
}
