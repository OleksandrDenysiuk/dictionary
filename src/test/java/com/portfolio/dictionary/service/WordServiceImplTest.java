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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        assertEquals(1,category.getWords().size());

        verify(userRepository,times(1)).findById(anyLong());
        verify(wordRepository,times(1)).save(any(Word.class));
    }

    @Test
    void update() {
        User user = User.builder().id(1L).categories(new HashSet<>()).build();
        Category category = Category.builder().id(1L).words(new HashSet<>()).build();
        Word word = Word.builder().id(1L).category(category).word("test").translation("translation2").build();
        category.getWords().add(word);
        category.setUser(user);
        user.getCategories().add(category);

        WordDto wordDto = WordDto.builder().id(1L).categoryId(1L).word("test2").translation("translation2").build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        wordService.update(wordDto, 1L);

        assertEquals("test2",word.getWord());
        assertEquals("translation2",word.getTranslation());
        verify(wordRepository,times(1)).save(any(Word.class));
        verify(userRepository,times(1)).findById(anyLong());
    }

    @Test
    void delete(){
        User user = User.builder().id(1L).categories(new HashSet<>()).build();
        Category category = Category.builder().id(1L).words(new HashSet<>()).build();
        Word word = Word.builder().id(1L).category(category).word("test").translation("translation2").build();
        category.getWords().add(word);
        category.setUser(user);
        user.getCategories().add(category);

        WordDto wordDto = WordDto.builder().id(1L).categoryId(1L).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        wordService.delete(wordDto,1L);

        assertEquals(0,category.getWords().size());

        verify(wordRepository,times(1)).delete(any(Word.class));
        verify(userRepository,times(1)).findById(anyLong());
    }
}