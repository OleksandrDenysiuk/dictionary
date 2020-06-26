package com.portfolio.dictionary.controller.api;

import com.google.gson.Gson;
import com.portfolio.dictionary.command.WordCommand;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.Role;
import com.portfolio.dictionary.service.WordService;
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
class WordRestControllerTest {
    @Mock
    WordService wordService;
    @InjectMocks
    WordRestController wordRestController;

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
        mockMvc = MockMvcBuilders.standaloneSetup(wordRestController).setCustomArgumentResolvers(putPrincipal).build();
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/api//categories/1/words"))
                .andExpect(status().isOk())
                .andReturn();
        verify(wordService,times(1)).getAllByCategoryIdAndUserId(anyLong(), anyLong());
    }

    @Test
    void getOne() throws Exception {

        mockMvc.perform(get("/api//categories/1/words/1"))
                .andExpect(status().isOk())
                .andReturn();
        verify(wordService,times(1)).getOneByIdAndCategoryIdAndUserId(anyLong(), anyLong(), anyLong());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/api//categories/1/words")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new Gson().toJson(new WordCommand())))
                .andExpect(status().isOk())
                .andReturn();
        verify(wordService,times(1)).create(any(), anyLong(), anyLong());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/api//categories/1/words/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new WordCommand())))
                .andExpect(status().isOk())
                .andReturn();
        verify(wordService,times(1)).update(any(), anyLong(), anyLong());
    }

    @Test
    void deleteWord() throws Exception {

        mockMvc.perform(delete("/api//categories/1/words/1"))
                .andExpect(status().isOk())
                .andReturn();
        verify(wordService,times(1)).delete(anyLong(), anyLong(), anyLong());
    }
}