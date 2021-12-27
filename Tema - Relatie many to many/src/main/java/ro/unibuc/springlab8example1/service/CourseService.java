package ro.unibuc.springlab8example1.service;

import org.springframework.stereotype.Service;
import ro.unibuc.springlab8example1.domain.Course;
import ro.unibuc.springlab8example1.dto.CourseDto;
import ro.unibuc.springlab8example1.exception.CourseNotFoundException;
import ro.unibuc.springlab8example1.mapper.CourseMapper;
import ro.unibuc.springlab8example1.repository.CourseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository repository;
    private final CourseMapper mapper;

    public CourseService(CourseRepository repository, CourseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CourseDto create(CourseDto courseDto) {
        Course course = mapper.mapToEntity(courseDto);

        Course savedCourse = repository.save(course);

        return mapper.mapToDto(savedCourse);
    }

    public List<CourseDto> loadAllCourses() {
        List<Course> courses = repository.findAll();
        return courses.stream().map(d -> mapper.mapToDto(d)).collect(Collectors.toList());
    }

    public Course loadCourseById (Long id) {
        return repository.findById(id).orElseThrow(() -> new CourseNotFoundException("course not found"));
    }
}
