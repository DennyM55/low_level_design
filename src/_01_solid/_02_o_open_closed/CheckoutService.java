package _01_solid._02_o_open_closed;

import java.math.BigDecimal;

class CheckoutService {
    BigDecimal finalAmount(BigDecimal cartAmount, DiscountPolicy discountPolicy) {
        return discountPolicy.apply(cartAmount);
    }
}
