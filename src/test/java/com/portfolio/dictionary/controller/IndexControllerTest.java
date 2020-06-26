package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.Role;
import com.portfolio.dictionary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {
    @Mock
    UserService userService;
    @InjectMocks
    IndexController indexController;

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
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).setCustomArgumentResolvers(putPrincipal).build();
    }

    @Test
    void welcome() throws Exception {
        when(userService.getOneByUsername(anyString())).thenReturn(UserDto.builder().id(1L).build());

        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }
}