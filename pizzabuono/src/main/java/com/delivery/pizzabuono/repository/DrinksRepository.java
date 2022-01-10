package com.delivery.pizzabuono.repository;

import com.delivery.pizzabuono.domain.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinksRepository extends JpaRepository<Drink, Long> {

    Optional<Drink> findByName(String name);

    void deleteByName(String name);
}
