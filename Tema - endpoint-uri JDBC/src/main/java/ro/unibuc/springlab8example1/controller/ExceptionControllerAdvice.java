package ro.unibuc.springlab8example1.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.unibuc.springlab8example1.exception.ErrorResponse;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNOTFound(Exception exception) {
        log.debug("User not found..");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .code(404)
                        .message(exception.getMessage())
                        .build());
    }
}
