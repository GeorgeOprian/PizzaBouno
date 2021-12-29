package com.delivery.pizzabuono.util;

import com.delivery.pizzabuono.dto.DrinkDto;
import com.delivery.pizzabuono.dto.PizzaDto;

public class DrinkDtoUtil {

    public static DrinkDto aDrinkDto(String name, Integer quantity, Double price) {
        return DrinkDto.builder()
                .name(name)
                .quantity(quantity)
                .price(price)
                .build();
    }

}
