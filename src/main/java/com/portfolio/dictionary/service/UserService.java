package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.User;

public interface UserService {
    void save(UserDto user);

    User findByUsername(String username);

    User findById(Long id);

    User update(User user);
}
