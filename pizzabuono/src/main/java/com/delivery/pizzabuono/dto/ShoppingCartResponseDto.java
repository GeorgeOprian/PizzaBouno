package com.delivery.pizzabuono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponseDto {

    private Long id;

    private List<PizzaDto> pizza;

    private List<DrinkDto> drinks;

    private Double total;

}
