package ro.unibuc.springlab8example1.util;

import ro.unibuc.springlab8example1.dto.UserDto;

public class UserDtoUtil {

    public static UserDto aUserDto(String username) {
        return UserDto.builder()
                .username(username)
                .cnp("123456789102")
                .otherInformation("info")
                .build();
    }
}
