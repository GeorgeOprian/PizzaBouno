package com.delivery.pizzabuono.exception;

public class ShoppingCartEmptyException extends RuntimeException {
    public ShoppingCartEmptyException(String message) {
        super(message);
    }
}
