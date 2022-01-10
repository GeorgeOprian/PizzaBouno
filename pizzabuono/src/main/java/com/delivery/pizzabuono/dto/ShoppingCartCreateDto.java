package com.delivery.pizzabuono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartCreateDto {

    @NotNull
    @NotEmpty
    private String userName;

    private List<String> pizza;

    private List<String> drinks;

    public boolean existsPizza(){
        return pizza != null && !pizza.isEmpty();
    }

    public boolean existsDrinks(){
        return drinks != null && !drinks.isEmpty();
    }

}
