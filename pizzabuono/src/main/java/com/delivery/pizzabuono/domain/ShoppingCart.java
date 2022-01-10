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
    @JoinTable(name = "shopping_cart_pizza",
                joinColumns = @JoinColumn(name = "cart_id"),
                inverseJoinColumns = @JoinColumn(name = "pizza_id")
            )
    private List<Pizza> pizza;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "shopping_cart_drink",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "drink_id")
    )
    private List<Drink> drinks;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "created_time")
    private LocalTime createdTime;

    @OneToOne(mappedBy = "shoppingCart")
    private User user;

    @OneToOne(mappedBy = "shoppingCart", fetch = FetchType.EAGER)
    private Order order;

    @Transient
    private Double total;

    public ShoppingCart() {
        pizza = new ArrayList<>();
        drinks = new ArrayList<>();
    }

    public void addPizza(Pizza aPizza) {
        pizza.add(aPizza);
    }

    public void addDrink(Drink aDrink) {
        drinks.add(aDrink);
    }

    public void calculateTotal(){

        double tot = 0;

        for (Pizza pizza: pizza){
            tot += pizza.getPrice();
        }

        for (Drink drink: drinks){
            tot += drink.getPrice();
        }
        total = tot;
    }
}
