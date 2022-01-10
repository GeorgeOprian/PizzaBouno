package com.delivery.pizzabuono.service;
import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import com.delivery.pizzabuono.dto.*;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.mapper.ProductsMapper;
import com.delivery.pizzabuono.repository.DrinksRepository;
import com.delivery.pizzabuono.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DrinksRepository drinksRepository;

    @Autowired
    private ProductsMapper productsMapper;

    public MenuDto loadMenu() {
        List<Pizza> pizza = pizzaRepository.findAll();
        List<Drink> drinks = drinksRepository.findAll();

        if(pizza.isEmpty() && drinks.isEmpty()) {
            throw new ProductNotFoundException("There are no products in the menu.");
        }

        if (pizza.isEmpty()) {
            throw new ProductNotFoundException("There is no pizza in the menu.");
        }
        if (drinks.isEmpty()) {
            throw new ProductNotFoundException("There are no drinks in the menu.");
        }

        MenuDto menuDto = new MenuDto();
        List<PizzaDto> pizzaDtos = pizza.stream().map(p -> productsMapper.mapToDto(p)).collect(Collectors.toList());
        List<DrinkDto> drinksDtos = drinks.stream().map(d -> productsMapper.mapToDto(d)).collect(Collectors.toList());
        menuDto.setPizza(pizzaDtos);
        menuDto.setDrinks(drinksDtos);

        return menuDto;
    }


}
