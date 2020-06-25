package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.ResultCommand;
import com.portfolio.dictionary.dto.ResultDto;
import com.portfolio.dictionary.model.Result;
import com.portfolio.dictionary.model.TestType;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.ResultRepository;
import com.portfolio.dictionary.repository.TestTypeRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultServiceImplTest {

    @Mock
    ResultRepository resultRepository;
    @Mock
    TestTypeRepository testTypeRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    ResultServiceImpl resultService;

    @Test
    void create() {
        User user = User.builder().id(1L).build();
        TestType testType = TestType.builder().typeName("TYPE_NAME").category(new HashSet<>()).build();
        Result result = Result.builder().id(1L).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(testTypeRepository.findByTypeName(anyString())).thenReturn(Optional.of(testType));
        when(resultRepository.save(any())).thenReturn(result);

        ResultDto resultDto = resultService.create(ResultCommand.builder()
                .testType("TEST_TYPE")
                .categoriesId(new ArrayList<>())
                .words(new ArrayList<>())
                .build(), 1L);

        assertNotNull(resultDto);
        verify(userRepository, times(1)).findById(anyLong());
        verify(testTypeRepository, times(1)).findByTypeName(anyString());
        verify(resultRepository, times(1)).save(any());

    }

    @Test
    void getOne() {
        User user = User.builder().id(1L).results(new HashSet<>()).build();
        Result result = Result.builder().id(1L).build();
        user.getResults().add(result);
        result.setUser(user);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        ResultDto resultDto = resultService.getOneByIdAndUserId(1L,1L);

        assertNotNull(resultDto);
        assertEquals(1,resultDto.getId());

        verify(userRepository,times(1)).findById(anyLong());
    }

    @Test
    void delete() {
        User user = User.builder().id(1L).results(new HashSet<>()).build();
        Result result = Result.builder().id(1L).build();
        user.getResults().add(result);
        result.setUser(user);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        resultService.delete(1L, 1L);

        assertEquals(0,user.getResults().size());
    }
}