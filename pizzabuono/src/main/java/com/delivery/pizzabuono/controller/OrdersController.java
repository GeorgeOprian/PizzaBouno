package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.domain.Order;
import com.delivery.pizzabuono.dto.OrderDto;
import com.delivery.pizzabuono.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService service;

    @PostMapping("/place_order")
    public ResponseEntity<OrderDto> placeOrder(@RequestParam String userName) {
        return ResponseEntity
                .ok()
                .body(service.createOrder(userName));
    }
}
