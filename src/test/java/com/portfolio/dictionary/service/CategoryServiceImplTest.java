package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    @Mock
    UserService userService;

    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(userService);
    }

    @Test
    void create() {
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).build();
        User userWithCategory = User.builder().id(1L).categories(Collections.singleton(category)).build();
        when(userService.findById(anyLong())).thenReturn(user);
        when(userService.update(any(User.class))).thenReturn(userWithCategory);

        categoryService.create("test", 1L);
        verify(userService, times(1)).findById(anyLong());
        verify(userService,times(1)).update(any(User.class));

    }
}