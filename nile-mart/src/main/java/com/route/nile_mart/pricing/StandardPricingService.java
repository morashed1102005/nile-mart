package com.route.nile_mart.pricing;

import com.route.nile_mart.common.Money;
import com.route.nile_mart.model.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Primary
public class StandardPricingService implements PricingService{
    @Override
    public BigDecimal getPrice(Product product, int quantity) {
        return Money.scale(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
    }
}
