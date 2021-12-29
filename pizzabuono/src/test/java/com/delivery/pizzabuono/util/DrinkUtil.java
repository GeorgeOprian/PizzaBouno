package com.delivery.pizzabuono.util;

import com.delivery.pizzabuono.domain.Drink;

public class DrinkUtil {

    public static Drink aDrink(String name, Integer quantity, Double price) {
        return Drink.builder()
                .name(name)
                .quantity(quantity)
                .price(price)
                .build();
    }

}
