package com.delivery.pizzabuono.mapper;

import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import com.delivery.pizzabuono.dto.DrinkDto;
import com.delivery.pizzabuono.dto.PizzaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsMapper {

    PizzaDto mapToDto(Pizza pizza);

    Pizza mapToEntity(PizzaDto pizzaDto);

    DrinkDto mapToDto(Drink drink);

    Drink mapToEntity(DrinkDto drinkDto);

}
