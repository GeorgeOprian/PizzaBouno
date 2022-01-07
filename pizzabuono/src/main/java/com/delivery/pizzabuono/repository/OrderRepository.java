package com.delivery.pizzabuono.repository;

import com.delivery.pizzabuono.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
