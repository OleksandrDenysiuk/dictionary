package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Word;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WordMapper {

    WordMapper INSTANCE = Mappers.getMapper(WordMapper.class);

    WordDto toDto(Word word);

    Word toEntity(WordDto dto);
}
