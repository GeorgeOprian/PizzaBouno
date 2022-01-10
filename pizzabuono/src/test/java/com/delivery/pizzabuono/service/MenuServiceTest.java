package com.delivery.pizzabuono.service;

import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import com.delivery.pizzabuono.dto.MenuDto;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.mapper.ProductsMapper;
import com.delivery.pizzabuono.repository.DrinksRepository;
import com.delivery.pizzabuono.repository.PizzaRepository;
import com.delivery.pizzabuono.util.DrinkUtil;
import com.delivery.pizzabuono.util.PizzaUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private DrinksRepository drinksRepository;

    @InjectMocks
    private MenuService menuService;

    @Mock
    private ProductsMapper productsMapper;

    @Test
    void test_loadMenu_whenPizzaAndDrinksExist() {
        //arrange
        List<Pizza> pizza = new ArrayList<>();
        List<Drink> drinks = new ArrayList<>();
        pizza.add(PizzaUtil.aPizza("Romana", "mozzarela, chorizo",20.0));
        drinks.add(DrinkUtil.aDrink("Coca Cola", 500, 5.0));

        Mockito.when(pizzaRepository.findAll()).thenReturn(pizza);
        Mockito.when(drinksRepository.findAll()).thenReturn(drinks);

       //act
        MenuDto result = menuService.loadMenu();

       //assert
        assertThat(result.getPizza()).isNotNull();
        assertThat(result.getDrinks()).isNotNull();
    }

    @Test
    void test_loadMenu_whenMenuIsEmpty() {
        //arrange
        List<Pizza> pizza = new ArrayList<>();
        List<Drink> drinks = new ArrayList<>();

        Mockito.when(pizzaRepository.findAll()).thenReturn(pizza);
        Mockito.when(drinksRepository.findAll()).thenReturn(drinks);

        //act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> menuService.loadMenu());

        //assert
        assertEquals("There are no products in the menu.", exception.getMessage());
    }

    @Test
    void test_loadMenu_whenPizzaDoesNotExists() {
        //arrange
        List<Pizza> pizza = new ArrayList<>();
        List<Drink> drinks = new ArrayList<>();
        drinks.add(DrinkUtil.aDrink("Coca Cola", 500, 5.0));

        Mockito.when(pizzaRepository.findAll()).thenReturn(pizza);
        Mockito.when(drinksRepository.findAll()).thenReturn(drinks);

        //act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> menuService.loadMenu());

        //assert
        assertEquals("There is no pizza in the menu.", exception.getMessage());
    }

    @Test
    void test_loadMenu_whenDrinksDoNotExist() {
        //arrange
        List<Pizza> pizza = new ArrayList<>();
        List<Drink> drinks = new ArrayList<>();
        pizza.add(PizzaUtil.aPizza("Romana", "mozzarela, chorizo",20.0));

        Mockito.when(pizzaRepository.findAll()).thenReturn(pizza);
        Mockito.when(drinksRepository.findAll()).thenReturn(drinks);

        //act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> menuService.loadMenu());

        //assert
        assertEquals("There are no drinks in the menu.", exception.getMessage());
    }
}