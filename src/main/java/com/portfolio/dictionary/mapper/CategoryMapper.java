package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryDto categoryDto);

    @Mapping(source = "user.id", target = "userId")
    CategoryDto toDto(Category category);
}