package com.delivery.pizzabuono.repository;

import com.delivery.pizzabuono.domain.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    Optional<Pizza> findByName(String name);
}
