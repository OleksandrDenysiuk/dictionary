package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;

public interface CategoryService {

    void create(String name, Long userId);

    CategoryDto findByCategoryIdAndUserId(Long categoryId, Long userId);
}
