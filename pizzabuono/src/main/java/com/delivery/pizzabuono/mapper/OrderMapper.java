package com.delivery.pizzabuono.mapper;

import com.delivery.pizzabuono.domain.Order;
import com.delivery.pizzabuono.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto mapToDto(Order receipt);

}
