package com.delivery.pizzabuono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    private UserDto user;

    private ShoppingCartResponseDto shoppingCart;
}
