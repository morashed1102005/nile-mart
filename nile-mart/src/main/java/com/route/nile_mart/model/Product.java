package com.route.nile_mart.model;

import lombok.*;

import java.math.BigDecimal;
@Getter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @EqualsAndHashCode.Include
    private final String sku;
    private final String name;
    private final Category category;
    private final BigDecimal price;
    @Setter
    private int stockQuantity;
}
