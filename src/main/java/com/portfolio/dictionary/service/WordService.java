package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.WordCommand;
import com.portfolio.dictionary.dto.WordDto;

import java.util.List;

public interface WordService {
    List<WordDto> getAllByCategoryIdAndUserId(Long categoryId, Long userId);

    WordDto getOneByIdAndCategoryIdAndUserId(Long wordId, Long categoryId, Long userId);

    WordDto create(WordCommand command, Long categoryId, Long userId);

    WordDto update(WordCommand command, Long categoryId, Long userId);

    void delete(Long wordId, Long categoryId, Long userId);
}
