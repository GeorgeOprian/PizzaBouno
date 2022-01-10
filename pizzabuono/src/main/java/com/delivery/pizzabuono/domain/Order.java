package com.delivery.pizzabuono.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_order")
public class Order {

    public static final double MIN_ORDER_VALUE = 40;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @Column(name = "total")
    private Double total;

    public void calculateTotal(){
        List<Pizza> pizzas = shoppingCart.getPizza();
        List<Drink> drinks = shoppingCart.getDrinks();

        double tot = 0;

        for (Pizza pizza: pizzas){
            tot += pizza.getPrice();
        }

        for (Drink drink: drinks){
            tot += drink.getPrice();
        }
        total = tot;
    }
}
