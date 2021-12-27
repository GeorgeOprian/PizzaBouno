package ro.unibuc.springlab8example1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UpdateUserDto;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.mapper.UserMapper;
import ro.unibuc.springlab8example1.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto create(UserDto userDto, UserType type) {
        User user = userMapper.mapToEntity(userDto);
        user.setUserType(type);
        User savedUser = userRepository.save(user);

        return userMapper.mapToDto(savedUser);
    }

    public UserDto getOne(String username) {
        return userMapper.mapToDto(userRepository.get(username));
    }

    public UserDto updateStudent(UpdateUserDto user, String oldUserName) {
        userMapper.mapToEntity(user);
        User updatedUser = userRepository.updateStudent(userMapper.mapToEntity(user), oldUserName);

        return userMapper.mapToDto(updatedUser);
    }

    public UserDto deleteUser(String userName) {
        return userMapper.mapToDto(userRepository.delete(userName));
    }

    public List<UserDto> getUserByType(UserType type) {
        return userMapper.mapToDto(userRepository.getByType(type));
    }
}
