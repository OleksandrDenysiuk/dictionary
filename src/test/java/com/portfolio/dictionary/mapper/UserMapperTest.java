package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {


    @BeforeEach
    void setUp() {
    }

    @Test
    void toDto() {
        User user = User.builder()
                .id(1L)
                .username("test")
                .email("teset@tes.com")
                .password("5")
                .build();

        UserDto userDto = UserMapper.INSTANCE.toDto(user);

        assertNotNull(userDto);
        assertEquals(user.getUsername(),userDto.getUsername());
        assertEquals(user.getEmail(),userDto.getEmail());
        assertEquals(user.getPassword(),userDto.getPassword());
        assertNotNull(userDto.getCategories());
        assertEquals(user.getCategories().size(),userDto.getCategories().size());
    }
}