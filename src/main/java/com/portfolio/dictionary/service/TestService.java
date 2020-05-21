package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.Test;
import com.portfolio.dictionary.model.TestType;

import java.util.Set;

public interface TestService {
    Test generate(TestType testType, Set<CategoryDto> categoryDtoSet);
}
