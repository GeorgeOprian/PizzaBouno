package com.delivery.pizzabuono.dto;

import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

//it's used to send back a response with the detailed information abou the pizza and drinks
public class ShoppingCartCreateResponseDto {

    private Long id;

    private List<PizzaDto> pizza;

    private List<DrinkDto> drinks;

    private UserDto user;

}
