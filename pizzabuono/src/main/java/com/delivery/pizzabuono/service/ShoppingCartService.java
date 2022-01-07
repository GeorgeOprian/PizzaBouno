package com.delivery.pizzabuono.service;

import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import com.delivery.pizzabuono.domain.ShoppingCart;
import com.delivery.pizzabuono.domain.User;
import com.delivery.pizzabuono.dto.ShoppingCartCreateDto;
import com.delivery.pizzabuono.dto.ShoppingCartCreateResponseDto;
import com.delivery.pizzabuono.dto.ShoppingCartResponseDto;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.exception.ShoppingCartEmptyException;
import com.delivery.pizzabuono.exception.UserNotFoundException;
import com.delivery.pizzabuono.mapper.ProductsMapper;
import com.delivery.pizzabuono.mapper.ShoppingCartMapper;
import com.delivery.pizzabuono.repository.DrinksRepository;
import com.delivery.pizzabuono.repository.PizzaRepository;
import com.delivery.pizzabuono.repository.ShoppingCartRepository;
import com.delivery.pizzabuono.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ShoppingCartService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DrinksRepository drinksRepository;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserRepository userRepository;

    public ShoppingCartCreateResponseDto addToCart(ShoppingCartCreateDto dto) {
        //verificarea asta trebuie facuta inainte de a plasa comanda
        if (dto.getId() != null && (!dto.existsPizza() || !dto.existsDrinks())) {
            throw new ShoppingCartEmptyException("Shopping cart with id " + dto.getId()
                    + " must contain at least a drink or a pizza");
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        if (dto.getId() != null) {
            shoppingCart.setId(dto.getId());
        }

        for (String pizzaName : dto.getPizza()) {
            Pizza pizza = pizzaRepository.findByName(pizzaName)
                    .orElseThrow(() -> new ProductNotFoundException("Pizza " + pizzaName + " was not found"));
            shoppingCart.addPizza(pizza);
        }
        for (String drinkName : dto.getDrinks()) {
            Drink drink = drinksRepository.findByName(drinkName)
                    .orElseThrow(() -> new ProductNotFoundException("Drink " + drinkName + " was not found"));
            shoppingCart.addDrink(drink);
        }

        User user = userRepository.findByUsername(dto.getUserName()).orElseThrow(() -> new UserNotFoundException("The user was not found"));
        user.setShoppingCart(shoppingCart);

        shoppingCart.setUser(user);
        shoppingCart.setCreatedDate(LocalDate.now());
        shoppingCart.setCreatedTime(LocalTime.now());

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.mapToDto(savedShoppingCart);

    }

    public ShoppingCartResponseDto getShoppingCartForUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("The user was not found"));
        return shoppingCartMapper.mapToResponseDto(user.getShoppingCart());
    }
}
