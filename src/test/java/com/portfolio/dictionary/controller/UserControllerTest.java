package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("authentication/registrationForm"));
    }

    @Test
    void testRegistrationSuccessful() throws Exception {

        mockMvc.perform(post("/registration")
                .param("username", "test")
                .param("email", "test@mail.com")
                .param("password", "5")
                .param("passwordConfirm", "5"))
                .andExpect(status().is3xxRedirection());
        verify(userService, times(1)).save(any(UserDto.class));
    }

    @Test
    void testRegistrationFailed() throws Exception {

        mockMvc.perform(post("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("authentication/registrationForm"));
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("authentication/loginForm"));
    }

    @Test
    void loginHasError() throws Exception {
        mockMvc.perform(get("/login")
                .param("error", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("authentication/loginForm"));
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(get("/login")
                .param("logout", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("authentication/loginForm"));
    }
}