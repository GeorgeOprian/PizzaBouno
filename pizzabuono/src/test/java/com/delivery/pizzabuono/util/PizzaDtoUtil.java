package com.delivery.pizzabuono.util;

import com.delivery.pizzabuono.dto.PizzaDto;

public class  PizzaDtoUtil {

    public static PizzaDto aPizzaDto(String name, String ingredients, Double price) {
        return PizzaDto.builder()
                .name(name)
                .ingredients(ingredients)
                .price(price)
                .build();
    }
}
