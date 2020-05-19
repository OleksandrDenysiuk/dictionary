package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.Role;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsernameIsFound() {
        Role roleUser = Role.builder().id(1L).name("USER").build();
        User user = User
                .builder()
                .id(1L)
                .username("test")
                .password("1")
                .active(true)
                .roles(Collections.singleton(roleUser))
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(user);

        AccountDetails userDetails = (AccountDetails) userDetailsService.loadUserByUsername("test");

        assertNotNull(userDetails);
        assertEquals("1",userDetails.getPassword());
        assertEquals("test",userDetails.getUsername());
        assertEquals(1,userDetails.getAuthorities().size());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void loadUserByUsernameNotFound() {

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("test");
        });
    }
}