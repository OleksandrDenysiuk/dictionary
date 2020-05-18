package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Word;

public interface WordService {
    Word save(WordDto dto, Long userId);

    WordDto update(WordDto dto, Long userId);

    void delete(WordDto dto, Long userId);
}
