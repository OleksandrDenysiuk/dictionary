package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.WordCommand;
import com.portfolio.dictionary.dto.WordDto;

import java.util.List;

public interface WordService {
    List<WordDto> findAll(Long categoryId, Long userId);

    WordDto findOne(Long wordId, Long categoryId, Long userId);

    WordDto create(WordCommand command, Long userId);

    WordDto update(WordCommand command, Long userId);

    void delete(Long wordId, Long categoryId, Long userId);
}
