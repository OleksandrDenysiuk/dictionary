package com.portfolio.dictionary.service;

import com.portfolio.dictionary.repository.TestTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestTypeServiceImpl implements TestTypeService {

    private final TestTypeRepository testTypeRepository;

    public TestTypeServiceImpl(TestTypeRepository testTypeRepository) {
        this.testTypeRepository = testTypeRepository;
    }

    @Override
    public List<String> getAll() {
        List<String> types = new ArrayList<>();
        testTypeRepository.findAll().forEach(testType -> types.add(testType.getTypeName()));
        return types;
    }

}
