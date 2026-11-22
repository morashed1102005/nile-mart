package com.route.nile_mart.cart;

import com.route.nile_mart.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public record CartView(int number, Customer customer, List<LineView> lines, int itemCount, BigDecimal subtotal,
                       boolean empty) {

    public CartView {
        lines = List.copyOf(lines);
    }
}
