package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.Test;
import com.portfolio.dictionary.model.TestType;
import com.portfolio.dictionary.model.Word;

import java.util.Set;

public interface TestService {
    Test generate(TestType testType, Long userId, Set<Word> wordSet);
}
