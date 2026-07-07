package com.denny.lld.fundamentals.solid.openclosed;

import java.math.BigDecimal;

class CheckoutService {
    BigDecimal finalAmount(BigDecimal cartAmount, DiscountPolicy discountPolicy) {
        return discountPolicy.apply(cartAmount);
    }
}
