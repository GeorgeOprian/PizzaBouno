package ro.unibuc.springlab8example1.util;

import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserDetails;

public class UserUtil {

    public static User aUser(String username) {
        return User.builder()
                .username(username)
                .userDetails(UserDetails.builder()
                        .age(20)
                        .cnp("123456789111")
                        .otherInformation("info")
                        .build()
                ).build();
    }

    public static User aUser(long id) {
        return User.builder()
                .id(id)
                .username("username")
                .userDetails(UserDetails.builder()
                        .age(20)
                        .cnp("123456789111")
                        .otherInformation("info")
                        .build()
                ).build();
    }
}
