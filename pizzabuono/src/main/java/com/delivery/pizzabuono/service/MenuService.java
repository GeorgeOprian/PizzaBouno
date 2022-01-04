package com.delivery.pizzabuono.service;
import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import com.delivery.pizzabuono.domain.ShoppingCart;
import com.delivery.pizzabuono.dto.*;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.exception.ShoppingCartEmptyException;
import com.delivery.pizzabuono.mapper.ProductsMapper;
import com.delivery.pizzabuono.mapper.ShoppingCartMapper;
import com.delivery.pizzabuono.repository.DrinksRepository;
import com.delivery.pizzabuono.repository.PizzaRepository;
import com.delivery.pizzabuono.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DrinksRepository drinksRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

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

    public ShoppingCartResponseDto addToCart(ShoppingCartDto dto) {
        //verificarea asta trebuie facuta inainte de a plasa comanda
//        if (dto.getId() != null && dto.getPizza().isEmpty() && dto.getDrinks().isEmpty()) {
//            throw new ShoppingCartEmptyException("Shopping cart with id" + dto.getId()
//                                                + " must contain at least a drink or a pizza");
//        }

        ShoppingCart shoppingCart = new ShoppingCart();

        for(String pizzaName: dto.getPizza()) {
            Pizza pizza = pizzaRepository.findByName(pizzaName)
                    .orElseThrow(() -> new ProductNotFoundException("Pizza " + pizzaName + " was not found"));
            shoppingCart.addPizza(pizza);
        }

        for(String drinkName: dto.getDrinks()) {
            Drink drink = drinksRepository.findByName(drinkName)
                    .orElseThrow(() -> new ProductNotFoundException("Drink " + drinkName + " was not found"));
            shoppingCart.addDrink(drink);
        }

        shoppingCart.setCreatedDate(LocalDate.now());
        shoppingCart.setCreatedTime(LocalTime.now());

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.mapToDto(savedShoppingCart);
    }
}
