package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.ResultCommand;
import com.portfolio.dictionary.dto.ResultDto;

public interface ResultService {

    ResultDto create(ResultCommand resultCommand, Long userId);

    ResultDto getOne(Long resultId, Long userId);
}
