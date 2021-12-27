package ro.unibuc.springlab8example1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.springlab8example1.domain.Course;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserDetails;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.CourseDto;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;
import ro.unibuc.springlab8example1.mapper.CourseMapper;
import ro.unibuc.springlab8example1.mapper.UserMapper;
import ro.unibuc.springlab8example1.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserMapper userMapper;

    public UserDto create(UserDto userDto, UserType type) {
        User user = userMapper.mapToEntity(userDto);
        user.setUserType(type);
        user.setAccountCreated(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return userMapper.mapToDto(savedUser);
    }

    public UserDto getOne(String username) {
        return userMapper.mapToDto(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(":(")));

    }

    public List<UserDto> getFilteredUsers(String name, UserType userType) {
        List<User> users = userRepository.filter(userType, name);

        return users.stream().map(u -> userMapper.mapToDto(u)).collect(Collectors.toList());
    }

    @Transactional
    public void removeOlderUsers(Long years) {
        LocalDateTime time = LocalDateTime.now().minusYears(years);
        userRepository.deleteOlderUsers(time);
    }

    @Transactional
    public void generateRandomUsers(boolean ok) {
        User randomUser1 = User.builder()
                .username("testingUser1")
                .userType(UserType.STUDENT)
                .userDetails(UserDetails.builder()
                        .age(20)
                        .cnp("253647484848484")
                        .otherInformation("info")
                        .build())
                .build();

        userRepository.save(randomUser1);

        if (!ok) {
            throw new RuntimeException("Smth bad happened...");
        }

        User randomUser2 = User.builder()
                .username("testingUser2")
                .userType(UserType.STUDENT)
                .userDetails(UserDetails.builder()
                        .age(30)
                        .cnp("83284248")
                        .otherInformation("info2")
                        .build())
                .build();

        userRepository.save(randomUser2);
    }

    public UserDto addCourse(String userName, Long courseId) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("User not found"));
        Course course = courseService.loadCourseById(courseId);
        user.addCourse(course);

        userRepository.save(user);

        User fromDbUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.mapToDto(fromDbUser);
    }
}
