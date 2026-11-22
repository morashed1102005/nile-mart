package com.route.nile_mart.shipping;

import com.route.nile_mart.common.Money;
import com.route.nile_mart.model.Governorate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShippingQuoteService {
        private final ShippingCalculator standardCalculator;
        private final ShippingCalculator freeCalculator;
        private final BigDecimal freeShippingThreshold;

        public ShippingQuoteService(
                ShippingCalculator standardCalculator,
                @Qualifier("freeShipping") ShippingCalculator freeCalculator,
                @Value("${store.free-shipping-threshold}") BigDecimal freeShippingThreshold) {
            this.standardCalculator = standardCalculator;
            this.freeCalculator = freeCalculator;
            this.freeShippingThreshold = freeShippingThreshold;
        }

    public BigDecimal quote(Governorate governorate, BigDecimal subtotal) {
        if (governorate.zone().isFreeShippingEligible()
                && subtotal.compareTo(freeShippingThreshold) >= 0) {
            return Money.scale(freeCalculator.calculate(governorate));
        }
        return Money.scale(standardCalculator.calculate(governorate));
    }
}
