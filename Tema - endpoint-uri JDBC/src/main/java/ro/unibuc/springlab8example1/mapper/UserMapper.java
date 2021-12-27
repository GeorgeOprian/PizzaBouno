package ro.unibuc.springlab8example1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.dto.UpdateUserDto;
import ro.unibuc.springlab8example1.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target =  "userType", source = "userType")
    @Mapping(target = "cnp", source = "userDetails.cnp")
    @Mapping(target = "age", source = "userDetails.age")
    @Mapping(target = "otherInformation", source = "userDetails.otherInformation")
    UserDto mapToDto(User user);

    @Mapping(target =  "userType", source = "userType")
    @Mapping(target = "cnp", source = "userDetails.cnp")
    @Mapping(target = "age", source = "userDetails.age")
    @Mapping(target = "otherInformation", source = "userDetails.otherInformation")
    List<UserDto> mapToDto(List<User> user);

    @Mapping(target = "userDetails.cnp", source = "cnp")
    @Mapping(target = "userDetails.age", source = "age")
    @Mapping(target = "userDetails.otherInformation", source = "otherInformation")
    User mapToEntity(UserDto user);

    @Mapping(target = "userDetails.age", source = "age")
    @Mapping(target = "userDetails.otherInformation", source = "otherInformation")
    User mapToEntity(UpdateUserDto user);
}
