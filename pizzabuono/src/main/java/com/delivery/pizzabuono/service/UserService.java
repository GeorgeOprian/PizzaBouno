package com.delivery.pizzabuono.service;

import com.delivery.pizzabuono.domain.User;
import com.delivery.pizzabuono.dto.UserDto;
import com.delivery.pizzabuono.exception.UserNotFoundException;
import com.delivery.pizzabuono.mapper.UserMapper;
import com.delivery.pizzabuono.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto create(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        user.setAccountCreated(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return userMapper.mapToDto(savedUser);
    }

    public UserDto getOne(String username) {
        return userMapper.mapToDto(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("The user was not found")));
    }

    public void delete(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("The user was not found"));

        userRepository.delete(user);
    }
}
