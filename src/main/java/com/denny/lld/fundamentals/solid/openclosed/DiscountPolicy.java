package com.denny.lld.fundamentals.solid.openclosed;

import java.math.BigDecimal;

interface DiscountPolicy {
    BigDecimal apply(BigDecimal amount);
}
