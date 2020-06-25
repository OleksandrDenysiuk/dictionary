package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.CategoryCommand;
import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.CategoryRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void getAllByUserId() {
        User user = User.builder()
                .id(1L)
                .categories(Collections.singleton(Category.builder().id(1L).build()))
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        List<CategoryDto> categoryDtoList = categoryService.getAllByUserId(1L);

        assertEquals(1, categoryDtoList.size());
        verify(userRepository,times(1)).findById(anyLong());
    }

    @Test
    void getOneByIdAndUserId() {
        User user = User.builder()
                .id(1L)
                .categories(Collections.singleton(Category.builder().id(1L).build()))
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        CategoryDto categoryDto = categoryService.getOneByIdAndUserId(1L, 1L);

        assertNotNull(categoryDto);
        assertEquals(1,categoryDto.getId());
        verify(userRepository,times(1)).findById(anyLong());
    }

    @Test
    void create() {
        User user = User.builder()
                .id(1L)
                .categories(new HashSet<>())
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(categoryRepository.save(any())).thenReturn(Category.builder().id(1L).build());

        CategoryDto categoryDto = categoryService.create(CategoryCommand.builder().categoryName("name").build(), 1L);

        assertNotNull(categoryDto);
        assertEquals(1, user.getCategories().size());
        verify(userRepository,times(1)).findById(anyLong());
        verify(categoryRepository,times(1)).save(any());
    }

    @Test
    void update() {
        Category category = Category.builder().id(1L).name("name").build();
        User user = User.builder()
                .id(1L)
                .categories(Collections.singleton(category))
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(categoryRepository.save(any())).thenReturn(Category.builder().id(1L).build());

        CategoryDto categoryDto = categoryService.update(CategoryCommand.builder().id(1L).categoryName("nameChanged").build(), 1L);

        assertNotNull(categoryDto);
        assertEquals("nameChanged", category.getName());
        verify(userRepository,times(1)).findById(anyLong());
        verify(categoryRepository,times(1)).save(any());
    }

    @Test
    void delete() {

        User user = User.builder()
                .id(1L)
                .categories(new HashSet<>())
                .build();
        Category category = Category.builder().id(1L).build();
        category.setUser(user);
        user.getCategories().add(category);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        categoryService.delete(1L, 1L);

        assertEquals(0, user.getCategories().size());
        verify(userRepository,times(1)).findById(anyLong());
        verify(categoryRepository,times(1)).delete(any());
    }
}