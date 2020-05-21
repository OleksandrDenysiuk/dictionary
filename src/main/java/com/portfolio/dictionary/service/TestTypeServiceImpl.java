package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.TestType;
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
        for(TestType type : testTypeRepository.findAll()){
            types.add(type.getTypeName());
        }
        return types;
    }

    @Override
    public TestType findByName(String name) {
        TestType testType = testTypeRepository.findByTypeName(name);
        if(testType != null){
            return testType;
        }else {
            throw new RuntimeException("Test type no found");
        }

    }
}
