package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.Role;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.RoleRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void create() {
        UserDto user = new UserDto();
        user.setUsername("username");
        user.setPassword("1");
        Role role = Role.builder().id(1L).build();
        User userReturned = User.builder().id(1L).build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("1");
        when(roleRepository.findByName(anyString())).thenReturn(role);
        when(userRepository.save(any())).thenReturn(userReturned);

        UserDto userDto = userService.create(user);

        assertNotNull(userDto);
        assertEquals(1, userDto.getId());
    }

    @Test
    void getOneByUsername() {
        User user = User.builder().id(1L).build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        UserDto userDto = userService.getOneByUsername("username");

        assertNotNull(userDto);
        assertEquals(1, userDto.getId());
    }

    @Test
    void getOneById() {
        User user = User.builder().id(1L).build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        UserDto userDto = userService.getOneById(1L);

        assertNotNull(userDto);
        assertEquals(1, userDto.getId());
    }

    @Test
    void loadUserByUsername() {
        User user = User.builder().id(1L).build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        AccountDetails accountDetails = userService.loadUserByUsername("username");

        assertNotNull(accountDetails);
        assertEquals(1, accountDetails.getUserId());
    }
}