package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.mapper.WordMapper;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.model.Word;
import com.portfolio.dictionary.repository.UserRepository;
import com.portfolio.dictionary.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WordServiceImpl implements WordService {
    private final UserRepository userRepository;
    private final WordRepository wordRepository;

    public WordServiceImpl(UserRepository userRepository, WordRepository wordRepository) {
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public WordDto save(WordDto dto, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Category> optionalCategory = user.getCategories()
                    .stream()
                    .filter(category -> dto.getCategoryId().equals(category.getId()))
                    .findFirst();

            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                Word word = WordMapper.INSTANCE.toEntity(dto);
                word.setCategory(category);
                category.getWords().add(word);
                return WordMapper.INSTANCE.toDto(wordRepository.save(word));
            } else {
                throw new RuntimeException("Category not found");
            }
        } else {
            throw new RuntimeException("User not fund");
        }
    }

    @Override
    public WordDto update(WordDto dto, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Category> optionalCategory = user.getCategories()
                    .stream()
                    .filter(category -> dto.getCategoryId().equals(category.getId()))
                    .findFirst();
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                Optional<Word> optionalWord = category.getWords()
                        .stream()
                        .filter(word -> word.getId().equals(dto.getId()))
                        .findFirst();
                if (optionalWord.isPresent()) {
                    Word toUpdate = optionalWord.get();
                    toUpdate.setWord(dto.getWord());
                    toUpdate.setTranslation(dto.getTranslation());
                    return WordMapper.INSTANCE.toDto(wordRepository.save(toUpdate));
                } else {
                    throw new RuntimeException("Word not found");
                }
            } else {
                throw new RuntimeException("Category not found");
            }
        } else {
            throw new RuntimeException("User not fund");
        }
    }

    @Override
    public void delete(WordDto dto, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Category> optionalCategory = user.getCategories()
                    .stream()
                    .filter(category -> dto.getCategoryId().equals(category.getId()))
                    .findFirst();

            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                Optional<Word> optionalWord = category.getWords()
                        .stream()
                        .filter(word -> word.getId().equals(dto.getId()))
                        .findFirst();
                if (optionalWord.isPresent()) {
                    Word toDelete = optionalWord.get();
                    toDelete.setCategory(null);
                    category.getWords().remove(toDelete);
                    wordRepository.delete(toDelete);
                } else {
                    throw new RuntimeException("Word not found");
                }
            } else {
                throw new RuntimeException("Category not found");
            }
        } else {
            throw new RuntimeException("User not fund");
        }
    }
}
