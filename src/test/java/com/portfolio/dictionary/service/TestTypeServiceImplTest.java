package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.TestType;
import com.portfolio.dictionary.repository.TestTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestTypeServiceImplTest {

    @Mock
    TestTypeRepository testTypeRepository;
    @InjectMocks
    TestTypeServiceImpl testTypeService;

    @Test
    void getAll() {
        TestType testType1 = TestType.builder().id(1L).typeName("TYPE_NAME_1").build();
        TestType testType2 = TestType.builder().id(2L).typeName("TYPE_NAME_2").build();
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType1);
        testTypeList.add(testType2);

        when(testTypeRepository.findAll()).thenReturn(testTypeList);
        List<String> list = testTypeService.getAll();

        assertEquals(2,list.size());
        verify(testTypeRepository,times(1)).findAll();
    }
}