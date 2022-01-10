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
import com.delivery.pizzabuono.util.DrinkDtoUtil;
import com.delivery.pizzabuono.util.DrinkUtil;
import com.delivery.pizzabuono.util.PizzaDtoUtil;
import com.delivery.pizzabuono.util.PizzaUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private DrinksRepository drinksRepository;

    @Mock
    private ProductsMapper productsMapper;

    @InjectMocks
    private ProductsService productsService;

    @Test
    void test_create_Pizza_If_Not_Exists() {
        //Arrange
        PizzaDto pizzaDto = PizzaDtoUtil.aPizzaDto("Diavola", "mozzarela, chorizo",20.0);
        Pizza pizza = PizzaUtil.aPizza("Diavola", "mozzarela, chorizo",20.0);
        Pizza savedPizza = pizza;

        Mockito.when(productsMapper.mapToEntity(pizzaDto)).thenReturn(pizza);
        Mockito.when(pizzaRepository.findByName(pizza.getName())).thenReturn(Optional.empty()); //check
        Mockito.when((pizzaRepository.save(pizza))).thenReturn(savedPizza);
        Mockito.when((productsMapper.mapToDto(savedPizza))).thenReturn(pizzaDto);

        //Act
        PizzaDto result = productsService.create(pizzaDto);

        //Assert
        assertThat(result).isNotNull();
        Mockito.verify(productsMapper).mapToEntity(pizzaDto);
        Mockito.verify(pizzaRepository).findByName(pizza.getName());
        Mockito.verify(pizzaRepository).save(pizza);
        Mockito.verify(productsMapper).mapToDto(savedPizza);
    }

    @Test
    void test_create_Pizza_If_Exists() {
        //Arrange
        PizzaDto pizzaDto = PizzaDtoUtil.aPizzaDto("Diavola", "mozzarela, chorizo",20.0);
        Pizza pizza = PizzaUtil.aPizza("Diavola", "mozzarela, chorizo",20.0);
        String pizzaName = "Diavola";

        Mockito.when(productsMapper.mapToEntity(pizzaDto)).thenReturn(pizza);
        Mockito.when(pizzaRepository.findByName(pizzaName)).thenReturn(Optional.of(pizza)); //check

        //Act
        ObjectAlreadyInDb exception = assertThrows(ObjectAlreadyInDb.class,
                () -> productsService.create(pizzaDto));

        //Assert
        assertEquals("Pizza " + pizzaName + " is already in the menu.", exception.getMessage());
    }

    @Test
    void test_loadPizza_when_pizza_exists() {
        //arrange
        String pizzaName = "Fungi";
        PizzaDto pizzaDto = PizzaDtoUtil.aPizzaDto(pizzaName, "mozzarela, chorizo",20.0);
        Pizza pizza = PizzaUtil.aPizza(pizzaName, "mozzarela, chorizo",20.0);

        Mockito.when(pizzaRepository.findByName(pizzaName)).thenReturn(Optional.of(pizza));
        Mockito.when(productsMapper.mapToDto(pizza)).thenReturn(pizzaDto);

        //act
        PizzaDto result = productsService.loadPizza(pizzaName);

        //Assert
        assertEquals(pizzaDto, result);
    }

    @Test
    void test_loadPizza_when_pizza_does_not_exists() {
        //arrange
        String pizzaName = "Diavola";
        Mockito.when(pizzaRepository.findByName(pizzaName)).thenReturn(Optional.empty());

        //act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productsService.loadPizza(pizzaName));

        //assert
        assertEquals("Pizza " + pizzaName + " was not found", exception.getMessage());
    }

    @Test
    void test_deletePizza_whenPizzaExists() {
        //arrange
        String pizzaName = "Fungi";
        Pizza pizza = PizzaUtil.aPizza(pizzaName, "mozzarela, chorizo",20.0);

        Mockito.when(pizzaRepository.findByName(pizzaName)).thenReturn(Optional.of(pizza));

        //act
        productsService.deletePizza(pizzaName);

        //Assert
        Mockito.verify(pizzaRepository, Mockito.times(1)).delete(pizza);
    }

    @Test
    void test_deletePizza_whenPizzaDoesNotExists() {
        //arrange
        String pizzaName = "Diavola";
        Mockito.when(pizzaRepository.findByName(pizzaName)).thenReturn(Optional.empty());

        //act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productsService.deletePizza(pizzaName));

        //assert
        assertEquals("Pizza " + pizzaName + " was not found", exception.getMessage());
    }

    //Drinks
    @Test
    void test_create_Drink_If_Not_Exists() {
        //Arrange
        DrinkDto drinkDto = DrinkDtoUtil.aDrinkDto("Coca Cola", 500, 5.0);
        Drink drink = DrinkUtil.aDrink("Coca Cola", 500, 5.0);
        Drink savedDrink = drink;

        Mockito.when(productsMapper.mapToEntity(drinkDto)).thenReturn(drink);
        Mockito.when(drinksRepository.findByName(drink.getName())).thenReturn(Optional.empty()); //check
        Mockito.when((drinksRepository.save(drink))).thenReturn(savedDrink);
        Mockito.when((productsMapper.mapToDto(savedDrink))).thenReturn(drinkDto);

        //Act
        DrinkDto result = productsService.create(drinkDto);

        //Assert
        assertThat(result).isNotNull();
        Mockito.verify(productsMapper).mapToEntity(drinkDto);
        Mockito.verify(drinksRepository).findByName(drink.getName());
        Mockito.verify(drinksRepository).save(drink);
        Mockito.verify(productsMapper).mapToDto(savedDrink);
    }

    @Test
    void test_create_Drink_If_Exists() {
        //Arrange
        DrinkDto drinkDto = DrinkDtoUtil.aDrinkDto("Coca Cola", 500, 5.0);
        Drink drink = DrinkUtil.aDrink("Coca Cola", 500, 5.0);
        String drinkName = "Coca Cola";

        Mockito.when(productsMapper.mapToEntity(drinkDto)).thenReturn(drink);
        Mockito.when(drinksRepository.findByName(drinkName)).thenReturn(Optional.of(drink)); //check

        //Act
        ObjectAlreadyInDb exception = assertThrows(ObjectAlreadyInDb.class,
                () -> productsService.create(drinkDto));

        //Assert
        assertEquals("Drink " + drinkName + " is already in the menu.", exception.getMessage());
    }

    @Test
    void test_loadDrink_when_drink_exists() {
        //arrange
        String drinkName = "Coca Cola";
        DrinkDto drinkDto = DrinkDtoUtil.aDrinkDto(drinkName, 500, 5.0);
        Drink drink = DrinkUtil.aDrink(drinkName, 500, 5.0);

        Mockito.when(drinksRepository.findByName(drinkName)).thenReturn(Optional.of(drink));
        Mockito.when(productsMapper.mapToDto(drink)).thenReturn(drinkDto);

        //act
        DrinkDto result = productsService.loadDrink(drinkName);

        //Assert
        assertEquals(drinkDto, result);
    }

    @Test
    void test_loadDrink_when_drink_does_not_exists() {
        //arrange
        String drinkName = "Coca Cola";
        Mockito.when(drinksRepository.findByName(drinkName)).thenReturn(Optional.empty());

        //act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productsService.loadDrink(drinkName));

        //assert
        assertEquals("Drink " + drinkName + " was not found", exception.getMessage());
    }

    @Test
    void test_deleteDrink_whenDrinkExists() {
        //arrange
        String drinkName = "Coca Cola";
        Drink drink = DrinkUtil.aDrink(drinkName, 500, 5.0);

        Mockito.when(drinksRepository.findByName(drinkName)).thenReturn(Optional.of(drink));

        //act
        productsService.deleteDrink(drinkName);

        //Assert
        Mockito.verify(drinksRepository, Mockito.times(1)).delete(drink);
    }

    @Test
    void test_deleteDrink_whenDrinkDoesNotExists() {
        //arrange
        String drinkName = "Coca Cola";
        Mockito.when(drinksRepository.findByName(drinkName)).thenReturn(Optional.empty());

        //act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productsService.deleteDrink(drinkName));

        //assert
        assertEquals("Drink " + drinkName + " was not found", exception.getMessage());
    }
}