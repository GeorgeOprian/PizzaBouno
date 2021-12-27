package ro.unibuc.springlab8example1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table("USER_DETAILS")
public class UserDetails {

    private Long id;
    private String cnp;
    private Integer age;
    private String otherInformation;
}
