package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.UserDto;

public interface UserService {
    UserDto save(UserDto user);

    UserDto findByUsername(String username);

    UserDto findById(Long id);
}
