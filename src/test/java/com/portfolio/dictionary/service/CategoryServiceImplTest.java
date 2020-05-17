package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    @Mock
    UserRepository userRepository;

    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(userRepository);
    }

    @Test
    void create() {
        Optional<User> user = Optional.of(User.builder().id(1L).build());
        Category category = Category.builder().id(1L).name("test").build();
        User userWithCategory = User.builder().id(1L).categories(Collections.singleton(category)).build();
        when(userRepository.findById(anyLong())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(userWithCategory);

        categoryService.create("test", 1L);
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository,times(1)).save(any(User.class));

    }

    @Test
    void findByCategoryIdAndUsername() {
        Category category = Category.builder().id(1L).build();
        User user = User.builder().id(1L).build();
        category.setUser(user);
        user.getCategories().add(category);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        CategoryDto categoryDto = categoryService.findByCategoryIdAndUserId(1L,1L);

        assertNotNull(categoryDto);
        assertEquals(1L,categoryDto.getId());
        assertEquals(1L,categoryDto.getUserId());
    }
}