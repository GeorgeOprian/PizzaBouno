package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.dto.MenuDto;
import com.delivery.pizzabuono.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService service;
    //get menu o lista sortimnte de pizza si una de bauturi

    @GetMapping
    public ResponseEntity<MenuDto> getMenu(){
        return ResponseEntity
                .ok()
                .body(service.loadMenu());
    }

    //adauga in cos

}
