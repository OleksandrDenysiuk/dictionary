package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.WordDto;

public interface WordService {
    WordDto save(WordDto dto, Long userId);

    WordDto update(WordDto dto, Long userId);

    void delete(WordDto dto, Long userId);
}
