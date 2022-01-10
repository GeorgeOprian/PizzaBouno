package com.delivery.pizzabuono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDto {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[\\.a-zA-Z ]*$")
    private String name;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[\\.a-zA-Z, ]*$")
    private String ingredients;

    @NotNull
    @Positive
    private Double price;
}
