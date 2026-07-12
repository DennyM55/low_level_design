package com.denny.lld.fundamentals.solid.openclosed;

import java.math.BigDecimal;
import java.math.RoundingMode;

class RegularCustomerDiscount implements DiscountPolicy {
    public BigDecimal apply(BigDecimal amount) {
        return percentageOff(amount, "5");
    }

    private BigDecimal percentageOff(BigDecimal amount, String percent) {
        return amount.subtract(amount.multiply(new BigDecimal(percent)).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
    }
}
