package _01_solid._02_o_open_closed;

import java.math.BigDecimal;
import java.math.RoundingMode;

class PremiumCustomerDiscount implements DiscountPolicy {
    public BigDecimal apply(BigDecimal amount) {
        return percentageOff(amount, "10");
    }

    private BigDecimal percentageOff(BigDecimal amount, String percent) {
        return amount.subtract(amount.multiply(new BigDecimal(percent)).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
    }
}
