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
@Table(name = "drink")
public class Drink {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private Double price;

    @ManyToMany(mappedBy = "drinks")
    private List<ShoppingCart> shoppingCarts;

}
