package com.delivery.pizzabuono.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "shopping_cart_product",
                joinColumns = @JoinColumn(name = "cart_id"),
                inverseJoinColumns = @JoinColumn(name = "pizza_id")
            )
    private List<Pizza> pizzas;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "shopping_cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "drink_id")
    )
    private List<Drink> drinks;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "created_time")
    private LocalTime createdTime;

    public ShoppingCart() {
        pizzas = new ArrayList<>();
        drinks = new ArrayList<>();
    }

    public void addPizza(Pizza aPizza) {
        pizzas.add(aPizza);
//        aPizza.getShoppingCarts().add(this);
    }

    public void addDrink(Drink aDrink) {
        drinks.add(aDrink);
//        aDrink.getShoppingCarts().add(this);
    }
}
