package ro.unibuc.springlab8example1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.springlab8example1.dto.CourseDto;
import ro.unibuc.springlab8example1.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity
                .ok()
                .body(service.create(courseDto));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity
                .ok()
                .body(service.loadAllCourses());
    }
}
