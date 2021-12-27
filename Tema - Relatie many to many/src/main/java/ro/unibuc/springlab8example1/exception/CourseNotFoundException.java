package ro.unibuc.springlab8example1.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}