package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.CategoryCommand;
import com.portfolio.dictionary.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllByUserId(Long userId);

    CategoryDto getOneByIdAndUserId(Long categoryId, Long userId);

    CategoryDto create(CategoryCommand categoryCommand, Long userId);

    CategoryDto update(CategoryCommand categoryCommand, Long userId);

    void delete(Long categoryId, Long userId);
}
