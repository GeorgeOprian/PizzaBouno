package com.delivery.pizzabuono.mapper;

import com.delivery.pizzabuono.domain.ShoppingCart;
import com.delivery.pizzabuono.dto.ShoppingCartCreateResponseDto;
import com.delivery.pizzabuono.dto.ShoppingCartResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    @Mapping(target = "user.address", source = "user.userDetails.address")
    @Mapping(target = "user.card", source = "user.userDetails.card")
    ShoppingCartCreateResponseDto mapToDto(ShoppingCart shoppingCart);

    ShoppingCartResponseDto mapToResponseDto(ShoppingCart shoppingCart);

}
