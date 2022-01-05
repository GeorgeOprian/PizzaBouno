package com.delivery.pizzabuono.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor

@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "price")
    private Double price;

//    @ManyToMany(mappedBy = "pizzas")
//    private List<ShoppingCart> shoppingCarts;

    public Pizza() {
//        shoppingCarts = new ArrayList<>();
    }

//    public void addShoppingCart(ShoppingCart shoppingCart){
//        shoppingCarts.add(shoppingCart);
//    }

}
