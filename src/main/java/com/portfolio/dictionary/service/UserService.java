package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto save(UserDto user);

    UserDto findByUsername(String username);

    UserDto findById(Long id);
}
