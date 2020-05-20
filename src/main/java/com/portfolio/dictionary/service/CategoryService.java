package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(String name, Long userId);

    CategoryDto findByCategoryIdAndUserId(Long categoryId, Long userId);

    CategoryDto update(String name, Long categoryId, Long userId);

    void delete(Long categoryId, Long userId);

    List<CategoryDto> getListByIdAndUserId(List<String> categoryIdList, Long userId);
}
