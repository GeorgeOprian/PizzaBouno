package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.exception.ErrorResponse;
import com.delivery.pizzabuono.exception.ObjectAlreadyInDb;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleProductNotFound(Exception e) {
        log.debug("Product not found... ");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                    .code(404)
                    .message(e.getMessage())
                    .build());
    }

    @ExceptionHandler({ObjectAlreadyInDb.class})
    public ResponseEntity<ErrorResponse> handleObjectAlreadyPresent(Exception e) {
        log.debug("Object already exists. ");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .code(409)
                        .message(e.getMessage())
                        .build());
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleNotValidObject(Exception e) {
        log.debug("Object is not valid. ");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build());
    }

}
