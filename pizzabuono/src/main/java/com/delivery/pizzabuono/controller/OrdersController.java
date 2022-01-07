package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.domain.Order;
import com.delivery.pizzabuono.dto.OrderDto;
import com.delivery.pizzabuono.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService service;

    @GetMapping("/place_order")
    public ResponseEntity<OrderDto> placeOrder(@RequestParam String userName) {
        return ResponseEntity
                .ok()
                .body(service.createOrder(userName));
    }
}
