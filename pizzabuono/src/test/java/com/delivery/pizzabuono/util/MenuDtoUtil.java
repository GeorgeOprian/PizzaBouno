package com.delivery.pizzabuono.util;

import com.delivery.pizzabuono.dto.DrinkDto;
import com.delivery.pizzabuono.dto.MenuDto;
import com.delivery.pizzabuono.dto.PizzaDto;

import java.util.ArrayList;
import java.util.List;

public class MenuDtoUtil {

    public static MenuDto aMenuDto() {
        PizzaDto aPizzaDto = PizzaDtoUtil.aPizzaDto("Romana", "mozzrella", 20.0);
        DrinkDto aDrinkDto = DrinkDtoUtil.aDrinkDto("Water", 500, 5.0);
        List<PizzaDto> pizzas = new ArrayList<>();
        List<DrinkDto> drinks = new ArrayList<>();
        pizzas.add(aPizzaDto);
        drinks.add(aDrinkDto);
        return MenuDto.builder().pizza(pizzas).drinks(drinks).build();
    }
}
