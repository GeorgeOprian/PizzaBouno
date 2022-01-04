package com.delivery.pizzabuono.mapper;

import com.delivery.pizzabuono.domain.User;
import com.delivery.pizzabuono.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address", source = "userDetails.address")
    @Mapping(target = "card", source = "userDetails.card")
    UserDto mapToDto(User user);

    @Mapping(target = "userDetails.address", source = "address")
    @Mapping(target = "userDetails.card", source = "card")
    User mapToEntity(UserDto user);

}
