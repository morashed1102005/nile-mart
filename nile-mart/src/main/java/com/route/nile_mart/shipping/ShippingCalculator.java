package com.route.nile_mart.shipping;

import com.route.nile_mart.model.Governorate;

import java.math.BigDecimal;

public interface ShippingCalculator {
    BigDecimal calculate(Governorate governorate);
}
