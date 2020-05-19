package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.service.CategoryService;
import com.portfolio.dictionary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    CategoryService categoryService;
    @Mock
    UserService userService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getOne() throws Exception {
        UserDto user = UserDto.builder().id(1L).username("test").build();
        CategoryDto category = CategoryDto.builder().id(1L).build();

        when(userService.findByUsername(anyString())).thenReturn(user);
        when(categoryService.findByCategoryIdAndUserId(anyLong(),anyLong())).thenReturn(category);

        mockMvc.perform(get("/category/1").param("username","test"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("category"))
                .andExpect(view().name("category/index"));
    }

    @Test
    void create() throws Exception {
        UserDto user = UserDto.builder().id(1L).username("test").build();
        when(userService.findByUsername(anyString())).thenReturn(user);
        mockMvc.perform(post("/category/create")
        .param("username","test")
        .param("categoryName", "categoryName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(categoryService,times(1)).create(anyString(),anyLong());
    }
}