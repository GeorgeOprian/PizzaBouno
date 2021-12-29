package com.delivery.pizzabuono.service;

import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import com.delivery.pizzabuono.dto.MenuDto;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.mapper.ProductsMapper;
import com.delivery.pizzabuono.repository.DrinksRepository;
import com.delivery.pizzabuono.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        menuDto.setPizza(pizza);
        menuDto.setDrinks(drinks);

        return menuDto;
    }
}
