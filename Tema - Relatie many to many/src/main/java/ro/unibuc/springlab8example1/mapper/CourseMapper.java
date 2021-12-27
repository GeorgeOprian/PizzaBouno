package ro.unibuc.springlab8example1.mapper;

import org.mapstruct.Mapper;
import ro.unibuc.springlab8example1.domain.Course;
import ro.unibuc.springlab8example1.dto.CourseDto;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto mapToDto(Course course);

    Course mapToEntity(CourseDto courseDto);
}
