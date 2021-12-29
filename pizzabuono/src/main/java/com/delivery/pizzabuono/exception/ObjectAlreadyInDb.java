package com.delivery.pizzabuono.exception;

public class ObjectAlreadyInDb extends RuntimeException {
    public ObjectAlreadyInDb(String message) {
        super(message);
    }
}

