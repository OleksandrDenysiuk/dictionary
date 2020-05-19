package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.service.UserService;
import com.portfolio.dictionary.service.WordService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class WordControllerTest {
    @Mock
    WordService wordService;

    @Mock
    UserService userService;

    @InjectMocks
    WordController wordController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(wordController).build();
    }

    @Test
    void delete() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(UserDto.builder().id(1L).build());

        mockMvc.perform(get("/category/1/word/1/delete")
                .param("username","test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category/1"));

        verify(userService,times(1)).findByUsername(anyString());
        verify(wordService,times(1)).delete(any(WordDto.class),anyLong());
    }

    @Test
    void create() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(UserDto.builder().id(1L).build());

        mockMvc.perform(post("/category/1/word/create")
                .param("username","test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category/1"));

        verify(userService,times(1)).findByUsername(anyString());
        verify(wordService,times(1)).save(any(WordDto.class),anyLong());
    }

    @Test
    void update() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(UserDto.builder().id(1L).build());

        mockMvc.perform(post("/category/1/word/1/update")
                .param("username","test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category/1"));

        verify(userService,times(1)).findByUsername(anyString());
        verify(wordService,times(1)).update(any(WordDto.class),anyLong());
    }
}