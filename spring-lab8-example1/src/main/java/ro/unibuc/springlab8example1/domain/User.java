package ro.unibuc.springlab8example1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table("USERS")
public class User {

    private Long id;
    private String username;
    private String fullName;
    private UserDetails userDetails;
    private UserType userType;
    private LocalDateTime accountCreated;
}
