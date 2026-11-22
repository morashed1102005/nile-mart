package com.route.nile_mart.pricing;

import com.route.nile_mart.common.Money;
import com.route.nile_mart.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PromotionalPricingService implements PricingService{
    private final BigDecimal discountPercent;

    public PromotionalPricingService(@Value("${store.discount-amount}") BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }
    @Override
    public BigDecimal getPrice(Product product, int quantity) {
        BigDecimal unitPrice = product.getPrice();
        BigDecimal multiplier = BigDecimal.ONE.subtract(
                discountPercent.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
        );
        return Money.scale(unitPrice.multiply(multiplier).multiply(BigDecimal.valueOf(quantity)));
    }
}
