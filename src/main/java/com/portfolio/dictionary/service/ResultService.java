package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.ResultCommand;
import com.portfolio.dictionary.dto.ResultDto;

public interface ResultService {

    ResultDto getOneByIdAndUserId(Long resultId, Long userId);

    ResultDto create(ResultCommand resultCommand, Long userId);

    void delete(Long resultId, Long userId);
}
