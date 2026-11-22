package com.route.nile_mart.pricing;

import com.route.nile_mart.model.Product;

import java.math.BigDecimal;

public interface PricingService {

    BigDecimal getPrice(Product product, int quantity);
}
