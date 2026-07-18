package com.denny.lld.fundamentals.solid.singleresponsibility;

import java.math.BigDecimal;

class Product {
    private final String name;
    private final BigDecimal price;

    Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    String getName() {
        return name;
    }

    BigDecimal getPrice() {
        return price;
    }
}
