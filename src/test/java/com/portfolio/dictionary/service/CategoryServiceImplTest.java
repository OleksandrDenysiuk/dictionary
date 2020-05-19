package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.CategoryRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(userRepository, categoryRepository);
    }

    @Test
    void create() {
        Optional<User> user = Optional.of(User.builder().id(1L).categories(new HashSet<>()).build());
        when(userRepository.findById(anyLong())).thenReturn(user);

        categoryService.create("test", 1L);

        assertEquals(1, user.get().getCategories().size());
        verify(userRepository, times(1)).findById(anyLong());
        verify(categoryRepository,times(1)).save(any(Category.class));

    }

    @Test
    void findByCategoryIdAndUsername() {
        Category category = Category.builder().id(1L).build();
        User user = User.builder().id(1L).build();
        category.setUser(user);
        user.getCategories().add(category);

        when(categoryRepository.findByIdAndUserId(anyLong(),anyLong())).thenReturn(category);

        CategoryDto categoryDto = categoryService.findByCategoryIdAndUserId(1L,1L);

        assertNotNull(categoryDto);
        assertEquals(1L,categoryDto.getId());
        assertEquals(1L,categoryDto.getUserId());
    }

    @Test
    void update(){
        Category category = Category.builder().id(1L).build();

        when(categoryRepository.findByIdAndUserId(anyLong(),anyLong())).thenReturn(category);

        categoryService.update("test",1L,1L);

        assertEquals(1L,category.getId());
        assertEquals("test",category.getName());
    }

    @Test
    void delete(){
        Category category = Category.builder().id(1L).build();
        User user = User.builder().id(1L).build();
        category.setUser(user);
        user.getCategories().add(category);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(categoryRepository.findByIdAndUserId(anyLong(),anyLong())).thenReturn(category);

        categoryService.delete(1L,1L);
        assertEquals(0,user.getCategories().size());
        assertNull(category.getUser());
    }
}