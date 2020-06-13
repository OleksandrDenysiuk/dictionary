package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Word;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WordMapper {

    WordMapper INSTANCE = Mappers.getMapper(WordMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    WordDto toDto(Word word);
}
