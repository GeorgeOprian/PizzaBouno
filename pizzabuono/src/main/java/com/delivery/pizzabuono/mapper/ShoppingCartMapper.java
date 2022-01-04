package com.delivery.pizzabuono.mapper;

import com.delivery.pizzabuono.domain.ShoppingCart;
import com.delivery.pizzabuono.dto.ShoppingCartDto;
import com.delivery.pizzabuono.dto.ShoppingCartResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    ProductsMapper PRODUCTS_MAPPER = Mappers.getMapper(ProductsMapper.class);

    ShoppingCartResponseDto mapToDto(ShoppingCart shoppingCart);

//    ShoppingCart mapToEntity(ShoppingCartDto shoppingCartDto);

}
