package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto getOneByUsername(String username);

    UserDto getOneById(Long id);

    UserDto create(UserDto user);
}
