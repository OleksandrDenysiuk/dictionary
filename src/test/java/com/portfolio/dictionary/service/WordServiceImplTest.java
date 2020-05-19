package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.model.Word;
import com.portfolio.dictionary.repository.UserRepository;
import com.portfolio.dictionary.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class WordServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    WordRepository wordRepository;

    WordService wordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        wordService = new WordServiceImpl(userRepository, wordRepository);
    }

    @Test
    void save() {
        WordDto wordDto = WordDto.builder().id(1L).categoryId(1L).word("word").translation("translation").build();
        Category category = Category.builder().id(1L).words(new HashSet<>()).build();
        User user = User.builder().id(1L).categories(Collections.singleton(category)).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        wordService.save(wordDto, 1L);

        verify(userRepository,times(1)).findById(anyLong());
        verify(wordRepository,times(1)).save(any(Word.class));
    }

    @Test
    void update() {

    }
}