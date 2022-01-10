package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.dto.UserDto;
import com.delivery.pizzabuono.exception.UserNotFoundException;
import com.delivery.pizzabuono.service.UserService;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createStudent(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto));
    }

    @GetMapping
    public ResponseEntity<UserDto> get(@RequestParam String username) {

        return ResponseEntity
                .ok()
                .body(userService.getOne(username));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam String username) {
        userService.delete(username);
    }

}
