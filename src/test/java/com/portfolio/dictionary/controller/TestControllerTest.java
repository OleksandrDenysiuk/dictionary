package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.Role;
import com.portfolio.dictionary.service.TestTypeService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {
    @Mock
    TestTypeService testTypeService;
    @Mock
    UserService userService;
    @InjectMocks
    TestController testController;

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
        mockMvc = MockMvcBuilders.standaloneSetup(testController).setCustomArgumentResolvers(putPrincipal).build();
    }

    @Test
    void getForm() throws Exception {
        when(userService.getOneById(anyLong())).thenReturn(UserDto.builder().id(1L).build());
        List<String> testTypes = new ArrayList<>();
        testTypes.add("TEST_TYPE");
        when(testTypeService.getAll()).thenReturn(testTypes);

        mockMvc.perform(get("/tests"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("types"))
                .andExpect(view().name("test/testForm"))
                .andExpect(status().isOk());

        verify(userService,times(1)).getOneById(anyLong());
        verify(testTypeService,times(1)).getAll();
    }

    @Test
    void process() throws Exception {
        when(userService.getOneById(anyLong())).thenReturn(UserDto.builder().id(1L).build());

        mockMvc.perform(get("/tests/start"))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("test/index"))
                .andExpect(status().isOk());

        verify(userService,times(1)).getOneById(anyLong());
    }

    @Test
    void result() throws Exception {
        when(userService.getOneById(anyLong())).thenReturn(UserDto.builder().id(1L).build());

        mockMvc.perform(get("/tests/results/1"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("resultId"))
                .andExpect(view().name("test/result"))
                .andExpect(status().isOk());

        verify(userService,times(1)).getOneById(anyLong());
    }
}