package ro.unibuc.springlab8example1.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;
import ro.unibuc.springlab8example1.mapper.UserMapper;
import ro.unibuc.springlab8example1.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ro.unibuc.springlab8example1.util.UserDtoUtil.aUserDto;
import static ro.unibuc.springlab8example1.util.UserUtil.aUser;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void test_create() {
        //Arrange
        UserDto userDto = aUserDto("Maria");
        UserType type = UserType.PROFESSOR;
        User user = aUser("Maria");
        User savedUser = aUser(1L);

        when(userMapper.mapToEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToDto(savedUser)).thenReturn(userDto);

        //Act
        UserDto result = userService.create(userDto, type);

        //Assert
        assertThat(result).isNotNull();
        verify(userMapper, times(1)).mapToEntity(userDto);
        verify(userMapper, times(1)).mapToDto(savedUser);
        verify(userRepository, times(1)).save(user);

        verifyNoMoreInteractions(userMapper, userRepository);
    }

    @Test
    void test_getOne_whenUsernameExists() {
        //arrange
        String username = "Maria";
        UserDto userDto = aUserDto(username);
        User user = aUser("Maria");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.mapToDto(user)).thenReturn(userDto);

        //Act
        UserDto result = userService.getOne(username);

        //Assert
        assertEquals(userDto, result);
    }

    @Test
    void test_getOne_whenUsernameDoesNotExist() {
        //arrange
        String username = "Maria";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        //Act
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.getOne(username));

        assertEquals(":(", exception.getMessage());
    }
}