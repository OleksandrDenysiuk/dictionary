package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final UserService userService;

    public CategoryServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void create(String name, Long userId) {
        User user = userService.findById(userId);
        Category category = Category.builder().name(name).user(user).build();
        user.getCategories().add(category);
        userService.update(user);
    }
}
