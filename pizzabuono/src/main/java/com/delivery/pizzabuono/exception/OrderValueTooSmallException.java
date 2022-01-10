package com.delivery.pizzabuono.exception;

public class OrderValueTooSmallException extends RuntimeException{
    public OrderValueTooSmallException(String message) {
        super(message);
    }
}
