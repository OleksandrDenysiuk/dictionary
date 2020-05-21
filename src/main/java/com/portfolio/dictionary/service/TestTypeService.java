package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.TestType;

import java.util.List;

public interface TestTypeService {
    List<String> getAll();
    TestType findByName(String name);
}
