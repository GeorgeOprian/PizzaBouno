package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.dto.MenuDto;
import com.delivery.pizzabuono.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService service;

    @GetMapping
    public ResponseEntity<MenuDto> getMenu(){
        return ResponseEntity
                .ok()
                .body(service.loadMenu());
    }

}
