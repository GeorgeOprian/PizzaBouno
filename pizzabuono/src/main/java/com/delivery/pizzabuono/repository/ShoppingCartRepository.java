package com.delivery.pizzabuono.repository;

import com.delivery.pizzabuono.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
