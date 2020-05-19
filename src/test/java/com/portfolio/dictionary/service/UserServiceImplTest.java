package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.Role;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.RoleRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, roleRepository, bCryptPasswordEncoder);
    }

    @Test
    void save() {

        UserDto userDto = UserDto.builder()
                .username("test")
                .password("1")
                .passwordConfirm("1")
                .email("test@test.com")
                .build();

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("1");
        when(roleRepository.findByName(anyString())).thenReturn(new Role());
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        userService.save(userDto);

        verify(userRepository, times(1)).save(any(User.class));
        verify(bCryptPasswordEncoder, times(1)).encode(anyString());
        verify(roleRepository, times(1)).findByName(anyString());
    }

    @Test
    void findByUsernameIsFound() {
        User user = User.builder().id(1L).username("Test").build();
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDto userFounded = userService.findByUsername("Test");

        assertNotNull(userFounded);
        assertEquals("Test", userFounded.getUsername());
    }

    @Test
    void findByUsernameNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.findByUsername("Test");
        });
    }

    @Test
    void findByIdIsFound() {
        Optional<User> user = Optional.of(User.builder().id(1L).build());
        when(userRepository.findById(anyLong())).thenReturn(user);

        UserDto userFounded = userService.findById(1L);

        assertNotNull(userFounded);
        assertEquals(1L, userFounded.getId());
    }

    @Test
    void findByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.findById(1L);
        });
    }
}