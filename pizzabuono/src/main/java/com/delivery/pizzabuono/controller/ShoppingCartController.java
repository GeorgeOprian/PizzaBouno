package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.dto.ShoppingCartCreateDto;
import com.delivery.pizzabuono.dto.ShoppingCartCreateResponseDto;
import com.delivery.pizzabuono.dto.ShoppingCartResponseDto;
import com.delivery.pizzabuono.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping_cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    @PostMapping("/add_to_cart")
    public ResponseEntity<ShoppingCartCreateResponseDto> addToCart(@RequestBody ShoppingCartCreateDto dto) {
        return ResponseEntity
                .ok()
                .body(service.addToCart(dto));
    }

    @GetMapping
    public ResponseEntity<ShoppingCartResponseDto> getShoppingCartForUser(
            @RequestParam String userName
    ) {
        return ResponseEntity
                    .ok()
                    .body(service.getShoppingCartForUser(userName));
    }
}
