package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    CategoryDto create(String name, Long userId);

    List<CategoryDto> findAll(Long userId);

    CategoryDto update(String name, Long categoryId, Long userId);

    void delete(Long categoryId, Long userId);

    Set<CategoryDto> getListByIdAndUserId(List<String> categoryIdList, Long userId);

    CategoryDto findByCategoryIdAndUserId(Long categoryId, Long userId);

}
