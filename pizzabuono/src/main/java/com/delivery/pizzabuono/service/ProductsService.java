package com.delivery.pizzabuono.service;

import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import com.delivery.pizzabuono.dto.DrinkDto;
import com.delivery.pizzabuono.dto.PizzaDto;
import com.delivery.pizzabuono.exception.ObjectAlreadyInDb;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.mapper.ProductsMapper;
import com.delivery.pizzabuono.repository.DrinksRepository;
import com.delivery.pizzabuono.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductsService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DrinksRepository drinksRepository;

    @Autowired
    private ProductsMapper productsMapper;

    public PizzaDto create(PizzaDto pizzaDto) {
        Pizza pizza = productsMapper.mapToEntity(pizzaDto);

        Optional<Pizza> existentPizza = pizzaRepository.findByName(pizza.getName());

        if (existentPizza.isPresent()) {
            throw new ObjectAlreadyInDb("Pizza " + pizza.getName() + " is already in the menu.");
        }

        Pizza savedPizza = pizzaRepository.save(pizza);

        return productsMapper.mapToDto(savedPizza);
    }

    public PizzaDto loadPizza(String name) {

        return productsMapper.mapToDto(pizzaRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Pizza " + name + " was not found")));

    }

    @Transactional
    public void deletePizza(String name) {
        Pizza pizzaToDelete = pizzaRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Pizza " + name + " was not found"));
        pizzaRepository.delete(pizzaToDelete);
    }

    public DrinkDto create(DrinkDto drinkDto) {
        Drink drink = productsMapper.mapToEntity(drinkDto);

        Optional<Drink> existentDrink = drinksRepository.findByName(drink.getName());

        if (existentDrink.isPresent()) {
            throw new ObjectAlreadyInDb("Drink " + drink.getName() + " is already in the menu.");
        }

        Drink savedDrink = drinksRepository.save(drink);

        return productsMapper.mapToDto(savedDrink);
    }

    public DrinkDto loadDrink(String name) {

        return productsMapper.mapToDto(drinksRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Drink " + name + " was not found")));

    }

    public void deleteDrink(String name) {
        Drink drinkToDelete = drinksRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Drink " + name + " was not found"));

        drinksRepository.delete(drinkToDelete);
    }
}
