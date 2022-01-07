package com.delivery.pizzabuono.mapper;

import com.delivery.pizzabuono.domain.ShoppingCart;
import com.delivery.pizzabuono.dto.ShoppingCartCreateResponseDto;
import com.delivery.pizzabuono.dto.ShoppingCartResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    ProductsMapper PRODUCTS_MAPPER = Mappers.getMapper(ProductsMapper.class);
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "user.address", source = "user.userDetails.address")
    @Mapping(target = "user.card", source = "user.userDetails.card")
    ShoppingCartCreateResponseDto mapToDto(ShoppingCart shoppingCart);

    ShoppingCartResponseDto mapToResponseDto(ShoppingCart shoppingCart);

}
