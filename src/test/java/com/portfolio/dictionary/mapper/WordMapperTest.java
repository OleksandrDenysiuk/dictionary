package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordMapperTest {

    @Test
    void toDto() {
        Word word = Word.builder()
                .id(1L)
                .word("word")
                .translation("translation")
                .category(Category.builder().id(1L).build())
                .build();

        WordDto wordDto = WordMapper.INSTANCE.toDto(word);

        assertNotNull(wordDto);
        assertEquals(word.getId(),wordDto.getId());
        assertEquals(word.getWord(),wordDto.getWord());
        assertEquals(wordDto.getTranslation(),wordDto.getTranslation());
        assertEquals(word.getCategory().getId(), wordDto.getId());
    }
}