package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.Category;

public interface CategoryService {

    Category create(String name, Long userId);

    CategoryDto findByCategoryIdAndUserId(Long categoryId, Long userId);

    Category update(String name, Long categoryId, Long userId);

    void delete(Long categoryId, Long userId);
}
