package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.domain.Drink;
import com.delivery.pizzabuono.dto.DrinkDto;
import com.delivery.pizzabuono.dto.PizzaDto;
import com.delivery.pizzabuono.exception.ObjectAlreadyInDb;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.exception.UserNotFoundException;
import com.delivery.pizzabuono.service.ProductsService;
import com.delivery.pizzabuono.util.DrinkDtoUtil;
import com.delivery.pizzabuono.util.PizzaDtoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = ProductsController.class)
public class ProductsControllerTest {

    @MockBean
    private ProductsService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    ProductsController productsController = mock(ProductsController.class);

    @Test
    @DisplayName("Testing creating a pizza when pizza doesn't exists.")
    void test_createPizza_happyFlow() throws Exception {

        //Arrange
        PizzaDto dto = PizzaDtoUtil.aPizzaDto("Diavola", "mozzarela, chorizo",20.0);
        when(service.create(any(PizzaDto.class))).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/products/pizza")
                                            .content(objectMapper.writeValueAsString(dto))
                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(status().isOk())
                                            .andReturn();
        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Testing creating a drink when pizza doesn't exists.")
    void test_createDrink_happyFlow() throws Exception {

        //Arrange
        DrinkDto dto = DrinkDtoUtil.aDrinkDto("water", 500,5.0);
        when(service.create(any(DrinkDto.class))).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/products/drinks")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Testing creating a pizza when pizza exists.")
    void test_createPizza_sadFlow() throws Exception {

        //Arrange
        PizzaDto dto = PizzaDtoUtil.aPizzaDto("Diavola", "mozzarela, chorizo",20.0);
        when(service.create(any(PizzaDto.class))).thenThrow(new ObjectAlreadyInDb("Pizza " + dto.getName() + " is already in the menu."));

        //Act
        MvcResult result = mockMvc.perform(post("/products/pizza")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
        //Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(result.getResponse().getContentAsString()).contains("Pizza " + dto.getName() + " is already in the menu.");
    }

    @Test
    @DisplayName("Testing creating a drink when drink exists.")
    void test_createDrink_sadFlow() throws Exception {

        //Arrange
        DrinkDto dto = DrinkDtoUtil.aDrinkDto("water", 500,5.0);
        when(service.create(any(DrinkDto.class))).thenThrow(new ObjectAlreadyInDb("Drink " + dto.getName() + " is already in the menu."));

        //Act
        MvcResult result = mockMvc.perform(post("/products/drinks")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
        //Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(result.getResponse().getContentAsString()).contains("Drink " + dto.getName() + " is already in the menu.");
    }

    @Test
    @DisplayName("Testing getting a pizza when pizza exists.")
    void test_getPizza() throws Exception  {
        String pizzaName = "Romana";
        PizzaDto pizzaDto = PizzaDtoUtil.aPizzaDto("Romana", "mozzarela", 20.0);

        when(service.loadPizza(pizzaName)).thenReturn(pizzaDto);

        MvcResult result = mockMvc.perform(get("/products/pizza")
                .param("name", pizzaName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(pizzaDto.getName())))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(pizzaDto));
    }

    @Test
    @DisplayName("Testing getting a drink when drink exists.")
    void test_getDrink() throws Exception  {
        String drinkName = "Water";
        DrinkDto drinkDto = DrinkDtoUtil.aDrinkDto("Water", 500, 20.0);

        when(service.loadDrink(drinkName)).thenReturn(drinkDto);

        MvcResult result = mockMvc.perform(get("/products/drinks")
                .param("name", drinkName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(drinkDto.getName())))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(drinkDto));
    }

    @Test
    @DisplayName("Testing getting a pizza when pizza does not exists.")
    void test_getPizza_whenPizzaDoesNotExists() throws Exception {
        String pizzaName = "Romana";
        when(service.loadPizza(pizzaName)).thenThrow(new ProductNotFoundException("Pizza " + pizzaName + " was not found"));

        MvcResult result = mockMvc.perform(get("/products/pizza").queryParam("name", "Romana"))
                .andExpect(status().isNotFound()).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Testing getting a drink when does not drink exists.")
    void test_getDrink_whenDrinkDoesNotExists() throws Exception {
        String drinkName = "Water";
        when(service.loadDrink(drinkName)).thenThrow(new ProductNotFoundException("Drink " + drinkName + " was not found"));

        MvcResult result = mockMvc.perform(get("/products/drinks").queryParam("name", "Water"))
                .andExpect(status().isNotFound()).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Testing deleting a pizza when pizza exists")
    void test_deletePizzaFromMenu_whenPizzaExists() throws Exception {

        String pizzaName = "Fungi";
        doNothing().when(productsController).deletePizzaFromMenu(pizzaName);

        MvcResult result  = mockMvc.perform(delete("/products/pizza").queryParam("name", "Romana"))
                .andExpect(status().isNoContent()).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Testing deleting a drink when drink exists")
    void test_deleteDrinFromMenu_whenDrinkExists() throws Exception {

        String drinkName = "Water";
        doNothing().when(productsController).deleteDrinkFromMenu(drinkName);

        MvcResult result  = mockMvc.perform(delete("/products/drinks").queryParam("name", "Water"))
                .andExpect(status().isNoContent()).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Testing deleting a pizza when pizza does not exists")
    void test_deletePizzaFromMenu_whenPizzaDoesNotExists() throws Exception {

        String pizzaName = "Romana";

        doThrow(new ProductNotFoundException("Pizza " + pizzaName + " was not found"))
                .when(service).deletePizza(pizzaName);

        MvcResult result  = mockMvc.perform(delete("/products/pizza").queryParam("name", pizzaName))
                .andExpect(status().isNotFound()).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Testing deleting a drink when drink does not exists")
    void test_deleteDrinkFromMenu_whenDrinkDoesNotExists() throws Exception {

        String drinkName = "Water";

        doThrow(new ProductNotFoundException("Drink " + drinkName + " was not found"))
                .when(service).deleteDrink(drinkName);

        MvcResult result  = mockMvc.perform(delete("/products/drinks").queryParam("name", drinkName))
                .andExpect(status().isNotFound()).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
