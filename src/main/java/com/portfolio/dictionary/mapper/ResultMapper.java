package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.dto.ResultDto;
import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.Result;
import com.portfolio.dictionary.model.Word;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResultMapper {
    ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);

    @Mappings
    ({@Mapping(source = "testType.typeName", target = "testType"),
    @Mapping(source = "categories", target = "categoriesDto")})
    ResultDto toDto(Result result);

    @Mapping(source = "user.id", target = "userId")
    CategoryDto toDto(Category category);

    @Mapping(source = "category.id", target = "categoryId")
    WordDto toDto(Word word);
}
