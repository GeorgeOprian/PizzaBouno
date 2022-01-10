package com.delivery.pizzabuono.controller;

import com.delivery.pizzabuono.dto.MenuDto;
import com.delivery.pizzabuono.exception.ProductNotFoundException;
import com.delivery.pizzabuono.service.MenuService;
import com.delivery.pizzabuono.util.MenuDtoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.*;

@WebMvcTest(controllers = MenuController.class)
public class MenuControllerTest {

    @MockBean
    private MenuService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    MenuController menuController = mock(MenuController.class);

    @Test
    @DisplayName("Getting the menu when is not empty")
    void test_getMenu_whenMenuIsNotEmpty() throws Exception{
        //Arrange
        MenuDto menuDto = MenuDtoUtil.aMenuDto();
        when(service.loadMenu()).thenReturn(menuDto);

        //Act
        MvcResult result = mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andReturn();
        //Assert
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(menuDto));
    }


    @Test
    @DisplayName("Getting the menu when is empty")
    void test_getMenu_whenMenuIsEmpty() throws Exception{
        //Arrange
        when(service.loadMenu()).thenThrow(new ProductNotFoundException("There are no products in the menu."));

        //Act
        MvcResult result = mockMvc.perform(get("/menu"))
                .andExpect(status().isNotFound()).andReturn();

        //Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
