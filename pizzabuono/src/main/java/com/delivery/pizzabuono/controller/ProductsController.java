package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.dto.DrinkDto;
import com.delivery.pizzabuono.dto.PizzaDto;
import com.delivery.pizzabuono.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService service;

    @PostMapping("/pizza")
    public ResponseEntity<PizzaDto> createPizza(@Valid
                                                @RequestBody
                                                PizzaDto pizzaDto) {
        return ResponseEntity
                .ok()
                .body(service.create(pizzaDto));
    }

    @GetMapping("/pizza")
    public ResponseEntity<PizzaDto> getPizza(@RequestParam String name) {
        return ResponseEntity
                .ok()
                .body(service.loadPizza(name));
    }

    @DeleteMapping("/pizza")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePizzaFromMenu(@RequestParam String name) {
        service.deletePizza(name);
    }

    @PostMapping("/drinks")
    public ResponseEntity<DrinkDto> createDrink(@Valid
                                                @RequestBody
                                                DrinkDto drinkDto) {
        return ResponseEntity
                .ok()
                .body(service.create(drinkDto));
    }

    @GetMapping("/drinks")
    public ResponseEntity<DrinkDto> getDrink(@RequestParam String name) {
        return ResponseEntity
                .ok()
                .body(service.loadDrink(name));
    }

    @DeleteMapping("/drinks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDrinkFromMenu(@RequestParam String name) {
        service.deleteDrink(name);
    }
}
