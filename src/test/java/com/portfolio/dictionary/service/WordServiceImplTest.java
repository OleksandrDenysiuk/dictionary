package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.WordCommand;
import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.model.Word;
import com.portfolio.dictionary.repository.UserRepository;
import com.portfolio.dictionary.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    WordRepository wordRepository;
    @InjectMocks
    WordServiceImpl wordService;

    @Test
    void create() {
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).user(user).words(new HashSet<>()).build();
        user.getCategories().add(category);
        Word word = Word.builder().id(1L).build();
        WordCommand wordCommand = new WordCommand();
        wordCommand.setWord("word");
        wordCommand.setTranslation("translation");


        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(wordRepository.save(any())).thenReturn(word);

        WordDto wordDto = wordService.create(wordCommand, 1L, 1L);

        assertNotNull(wordDto);
        assertEquals(1,wordDto.getId());
        verify(userRepository,times(1)).findById(anyLong());
        verify(wordRepository,times(1)).save(any());
    }

    @Test
    void getAllByCategoryIdAndUserId() {
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).user(user).words(new HashSet<>()).build();
        Word word1 = Word.builder().id(1L).category(category).build();
        Word word2 = Word.builder().id(2L).category(category).build();
        category.getWords().add(word1);
        category.getWords().add(word2);
        user.getCategories().add(category);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        List<WordDto> wordDtoList = wordService.getAllByCategoryIdAndUserId(1L, 1L);

        assertEquals(2, wordDtoList.size());
        verify(userRepository,times(1)).findById(anyLong());
    }

    @Test
    void getOneByIdAndCategoryIdAndUserId() {
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).user(user).words(new HashSet<>()).build();
        Word word = Word.builder().id(1L).category(category).build();
        category.getWords().add(word);
        user.getCategories().add(category);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        WordDto wordDto = wordService.getOneByIdAndCategoryIdAndUserId(1L,1L, 1L);

        assertNotNull(wordDto);
        assertEquals(1, wordDto.getId());
        verify(userRepository,times(1)).findById(anyLong());
    }

    @Test
    void update() {
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).user(user).words(new HashSet<>()).build();
        Word word = Word.builder().id(1L).word("word").translation("translation").category(category).build();
        category.getWords().add(word);
        user.getCategories().add(category);
        WordCommand wordCommand = new WordCommand();
        wordCommand.setId(1L);
        wordCommand.setWord("wordChanged");
        wordCommand.setTranslation("translationChanged");


        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(wordRepository.save(any())).thenReturn(word);

        WordDto wordDto = wordService.update(wordCommand, 1L, 1L);

        assertNotNull(wordDto);
        assertEquals(1,wordDto.getId());
        assertEquals("wordChanged", word.getWord());
        assertEquals("translationChanged", word.getTranslation());
        verify(userRepository,times(1)).findById(anyLong());
        verify(wordRepository,times(1)).save(any());
    }

    @Test
    void delete() {
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).user(user).words(new HashSet<>()).build();
        Word word = Word.builder().id(1L).word("word").translation("translation").category(category).build();
        category.getWords().add(word);
        user.getCategories().add(category);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        wordService.delete(1L,1L,1L);

        assertEquals(0,category.getWords().size());
    }
}