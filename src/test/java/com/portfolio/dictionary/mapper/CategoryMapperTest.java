package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.model.Word;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryMapperTest {

    @Test
    void toDto() {
        Category category = Category.builder()
                .id(1L)
                .name("test")
                .user(User.builder().id(1L).build())
                .words(Collections.singleton(Word.builder().id(1L).build()))
                .build();

        CategoryDto categoryDto = CategoryMapper.INSTANCE.toDto(category);

        assertNotNull(categoryDto);
        assertEquals(category.getId(),categoryDto.getId());
        assertEquals(category.getName(),categoryDto.getName());
        assertEquals(category.getUser().getId(),categoryDto.getUserId());
        assertEquals(categoryDto.getWords().size(),categoryDto.getWords().size());
    }
}