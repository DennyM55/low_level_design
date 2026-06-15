package _01_solid._02_o_open_closed;

import java.math.BigDecimal;

interface DiscountPolicy {
    BigDecimal apply(BigDecimal amount);
}
