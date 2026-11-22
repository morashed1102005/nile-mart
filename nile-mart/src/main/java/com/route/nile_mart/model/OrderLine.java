package com.route.nile_mart.model;

import com.route.nile_mart.common.Money;

import java.math.BigDecimal;

public record OrderLine(String sku, String name, int quantity, BigDecimal linePrice) {
    public OrderLine {
        linePrice = Money.scale(linePrice);
    }
}
