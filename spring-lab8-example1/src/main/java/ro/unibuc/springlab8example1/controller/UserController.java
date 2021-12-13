package ro.unibuc.springlab8example1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UpdateUserDto;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/student")
    public ResponseEntity<UserDto> createStudent(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto, UserType.STUDENT));
    }

    @PostMapping("/professor")
    public ResponseEntity<UserDto> createProfessor(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto, UserType.PROFESSOR));
    }


    @GetMapping("/{username}")
    public ResponseEntity<UserDto> get(@PathVariable String username) {
        return ResponseEntity
                .ok()
                .body(userService.getOne(username));
    }

    //TODO
    // endpoints for updating a user
    @PatchMapping("/updatestudent/{old_user_name}")
    public ResponseEntity <UserDto> updateStudent(@RequestBody UpdateUserDto user,
                                                  @PathVariable(name = "old_user_name") String oldUserName) {
        return ResponseEntity.ok(userService.updateStudent(user, oldUserName));
    }

    //TODO
    // endpoints for deleting a user
    @DeleteMapping("/{user_name}")
    public ResponseEntity <UserDto> deleteUser(@PathVariable(name = "user_name") String userName) {
        return ResponseEntity.ok(userService.deleteUser(userName));
    }

    //TODO
    // get all users filtered by type
    @GetMapping("/types/{type}")
    public ResponseEntity <List<UserDto>> getUsersByType(@PathVariable(name = "type") UserType type) {
        return ResponseEntity.ok(userService.getUserByType(type));
    }

    // TODO: homework: endpoints for updating a user, deleting one, get all users filtered by type
}
