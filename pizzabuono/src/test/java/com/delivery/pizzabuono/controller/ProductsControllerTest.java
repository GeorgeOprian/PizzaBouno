package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.dto.PizzaDto;
import com.delivery.pizzabuono.exception.ObjectAlreadyInDb;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.exception.UserNotFoundException;
import com.delivery.pizzabuono.service.ProductsService;
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
    @DisplayName("Testing creating a pizza when pizza doesn't exists")
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
    @DisplayName("Testing creating a pizza when pizza exists")
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
    void test_getPizza() throws Exception  {
        String pizzaName = "Romana";
        PizzaDto pizzaDto = PizzaDtoUtil.aPizzaDto("Romana", "mozzarela", 20.0);

        when(service.loadPizza(pizzaName)).thenReturn(pizzaDto);

        mockMvc.perform(get("/products/pizza").queryParam("name", pizzaName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(pizzaDto.getName())));
    }

    @Test
    void test_getPizza_whenPizzaDoesNotExists() throws Exception {
        String pizzaName = "Romana";
        when(service.loadPizza(pizzaName)).thenThrow(new ProductNotFoundException("Pizza " + pizzaName + " was not found"));

        mockMvc.perform(get("/products/pizza").queryParam("name", "Romana"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_deletePizzaFromMenu_whenPizzaExists() throws Exception {

        String pizzaName = "Fungi";
//        when(service.deletePizza(pizzaName)).thenThrow(new ProductNotFoundException("Pizza " + pizzaName + " was not found")));

//        doThrow(new ProductNotFoundException("Pizza " + pizzaName + " was not found")).when(productsController).deletePizzaFromMenu(pizzaName);
        doNothing().when(productsController).deletePizzaFromMenu(pizzaName);

        MvcResult result  = mockMvc.perform(delete("/products/pizza").queryParam("name", "Romana"))
                .andExpect(status().isNoContent()).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void test_deletePizzaFromMenu_whenPizzaDoesNotExists() throws Exception {

        String pizzaName = "Romana";

        doThrow(new ProductNotFoundException("Pizza " + pizzaName + " was not found"))
                .when(service).deletePizza(pizzaName);

        MvcResult result  = mockMvc.perform(delete("/products/pizza").queryParam("name", pizzaName))
                .andExpect(status().isNotFound()).andReturn();

    }
}
