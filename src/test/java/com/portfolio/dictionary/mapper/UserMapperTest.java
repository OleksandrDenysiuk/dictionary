package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {


    @BeforeEach
    void setUp() {
    }

    @Test
    void toEntity() {

        UserDto userDto = new UserDto("test", "test@test.com", "5", "5", new HashSet<>());

        //when
        User user = UserMapper.INSTANCE.toEntity(userDto);

        assertNotNull(user);

        assertEquals("test", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("5", user.getPassword());
    }
}