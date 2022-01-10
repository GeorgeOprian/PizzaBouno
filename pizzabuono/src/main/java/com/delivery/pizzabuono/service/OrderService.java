package com.delivery.pizzabuono.service;

import com.delivery.pizzabuono.domain.Order;
import com.delivery.pizzabuono.domain.ShoppingCart;
import com.delivery.pizzabuono.domain.User;
import com.delivery.pizzabuono.dto.OrderDto;
import com.delivery.pizzabuono.exception.OrderValueTooSmallException;
import com.delivery.pizzabuono.exception.ShoppingCartEmptyException;
import com.delivery.pizzabuono.exception.UserNotFoundException;
import com.delivery.pizzabuono.mapper.OrderMapper;
import com.delivery.pizzabuono.mapper.ShoppingCartMapper;
import com.delivery.pizzabuono.mapper.UserMapper;
import com.delivery.pizzabuono.repository.OrderRepository;
import com.delivery.pizzabuono.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    public OrderDto createOrder(String userName) {

        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("The user was not found"));

        Order order = null;
        if (user.getShoppingCart().getOrder() != null) {
            order = user.getShoppingCart().getOrder();
        } else {
            order = new Order();
        }

        if (user.getShoppingCart() == null) {
            throw new ShoppingCartEmptyException("Shopping cart is empty");
        }
        if (user.getShoppingCart().getPizza().isEmpty()
                && user.getShoppingCart().getDrinks().isEmpty()) {
            throw new ShoppingCartEmptyException("Shopping cart is empty");
        }
        user.getShoppingCart().calculateTotal();

        order.setShoppingCart(user.getShoppingCart());
        order.calculateTotal();

        if (order.getTotal() < Order.MIN_ORDER_VALUE) {
            throw new OrderValueTooSmallException("Order total has to be at least " + Order.MIN_ORDER_VALUE + " to place an order");
        }

        OrderDto dto = createOrderDto(order, user, user.getShoppingCart());
        return dto;
    }

    private OrderDto createOrderDto(Order order, User user, ShoppingCart shoppingCart) {
        OrderDto dto = orderMapper.mapToDto(order);

        dto.setUser(userMapper.mapToDto(user));
        dto.setShoppingCart(shoppingCartMapper.mapToResponseDto(shoppingCart));
        return dto;

    }
}
