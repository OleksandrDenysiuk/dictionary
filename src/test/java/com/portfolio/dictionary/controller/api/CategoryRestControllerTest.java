package com.portfolio.dictionary.controller.api;

import com.google.gson.Gson;
import com.portfolio.dictionary.command.CategoryCommand;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.Role;
import com.portfolio.dictionary.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {
    @Mock
    CategoryService categoryService;
    @InjectMocks
    CategoryRestController categoryRestController;

    MockMvc mockMvc;

    HandlerMethodArgumentResolver putPrincipal;


    @BeforeEach
    void setUp() {

        putPrincipal = new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType().isAssignableFrom(AccountDetails.class);
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                          NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                return new AccountDetails(1L, "u", "1", true, Collections.singleton(Role.builder().id(1L).name("USER").build()));
            }
        };
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController).setCustomArgumentResolvers(putPrincipal).build();
    }

    @Test
    void getCategories() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andReturn();

        verify(categoryService, times(1)).getAllByUserId(anyLong());
    }

    @Test
    void getCategory() throws Exception {

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andReturn();

        verify(categoryService, times(1)).getOneByIdAndUserId(anyLong(), anyLong());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new CategoryCommand())))
                .andExpect(status().isOk())
                .andReturn();
        verify(categoryService,times(1)).create(any(), anyLong());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new CategoryCommand())))
                .andExpect(status().isOk())
                .andReturn();
        verify(categoryService,times(1)).update(any(), anyLong());
    }

    @Test
    void deleteCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk());
        verify(categoryService,times(1)).delete(anyLong(), anyLong());
    }
}