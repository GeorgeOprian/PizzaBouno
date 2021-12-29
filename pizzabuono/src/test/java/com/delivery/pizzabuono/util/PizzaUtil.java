package com.delivery.pizzabuono.util;

import com.delivery.pizzabuono.domain.Pizza;
import com.delivery.pizzabuono.dto.PizzaDto;

public class PizzaUtil {

    public static Pizza aPizza(String name, String ingredients, Double price) {
        return Pizza.builder()
                .name(name)
                .ingredients(ingredients)
                .price(price)
                .build();
    }

}
