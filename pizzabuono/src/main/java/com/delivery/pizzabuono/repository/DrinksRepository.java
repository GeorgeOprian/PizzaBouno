package com.delivery.pizzabuono.repository;

import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.domain.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinksRepository extends JpaRepository<Drink, Integer> {

    Optional<Drink> findByName(String name);

    void deleteByName(String name);
}
