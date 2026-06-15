package _01_solid._01_s_single_responsibility;

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
