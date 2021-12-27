package ro.unibuc.springlab8example1.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ro.unibuc.springlab8example1.dto.UserDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.unibuc.springlab8example1.util.UserDtoUtil.aUserDto;

class UserDtoValidationTest {

    private Validator validator;
    private UserDto request;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        request = aUserDto("Maria");
    }

    @Test
    @DisplayName("Test request when all fields are valid")
    void test_request_whenIsValid() {
        //Arrange

        //Act
        Set<ConstraintViolation<UserDto>> violations = validator.validate(request);

        //Assert
        assertThat(violations).isEmpty();
    }

    @Test
    void test_request_whenUsername_isInvalid() {
        request.setUsername(null);

        //Act
        Set<ConstraintViolation<UserDto>> violations = validator.validate(request);

        //Assert
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void test_request_whenUsername_andCnpAreInvalid() {
        request.setUsername(null);
        request.setCnp("12345678910442");

        //Act
        Set<ConstraintViolation<UserDto>> violations = validator.validate(request);

        //Assert
        assertThat(violations.size()).isEqualTo(2);
    }
}
