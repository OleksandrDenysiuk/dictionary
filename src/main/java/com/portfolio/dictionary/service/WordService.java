package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.WordDto;

import java.util.List;

public interface WordService {
    List<WordDto> findAll(Long categoryId, Long userId);

    WordDto findOne(Long wordId, Long categoryId, Long userId);

    WordDto create(WordDto dto, Long userId);

    WordDto update(WordDto dto, Long userId);

    void delete(WordDto dto, Long userId);
}
