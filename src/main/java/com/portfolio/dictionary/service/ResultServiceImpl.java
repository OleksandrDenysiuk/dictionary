package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.ResultCommand;
import com.portfolio.dictionary.dto.ResultDto;
import com.portfolio.dictionary.mapper.ResultMapper;
import com.portfolio.dictionary.model.*;
import com.portfolio.dictionary.repository.ResultRepository;
import com.portfolio.dictionary.repository.TestTypeRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final TestTypeRepository testTypeRepository;
    private final UserRepository userRepository;

    public ResultServiceImpl(ResultRepository resultRepository, TestTypeRepository testTypeRepository, UserRepository userRepository) {
        this.resultRepository = resultRepository;
        this.testTypeRepository = testTypeRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ResultDto create(ResultCommand resultCommand, Long userId) {

        Result result = new Result();

        result.setStartTime(resultCommand.getStartTime());
        result.setFinishTime(resultCommand.getFinishTime());

        TestType testType = testTypeRepository.findByTypeName(resultCommand.getTestType());
        if (testType != null) {
            result.setTestType(testType);
        }

        int points = resultCommand.getWords().size();

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            result.setUser(user);

            for (String id : resultCommand.getCategoriesId()) {
                Optional<Category> optionalCategory = user.getCategories().stream()
                        .filter(category -> category.getId().equals(Long.valueOf(id)))
                        .findFirst();

                if (optionalCategory.isPresent()) {
                    Category category = optionalCategory.get();
                    result.getCategories().add(category);

                    for (int i = 0; i < resultCommand.getWords().size(); i++) {
                        final int k = i;
                        Optional<Word> optionalWord = category.getWords().stream()
                                .filter(word -> word.getWord().equals(resultCommand.getWords().get(k)))
                                .findFirst();

                        if (optionalWord.isPresent()) {
                            Word word = optionalWord.get();

                            if (!word.getTranslation().equals(resultCommand.getAnswers().get(i))) {
                                points--;
                            }
                        }
                    }
                }
            }

            result.setPoints(points);
            return ResultMapper.INSTANCE.toDto(resultRepository.save(result));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public ResultDto getOne(Long resultId, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Result> optionalResult = user.getResults().stream()
                    .filter(result -> result.getId().equals(resultId))
                    .findFirst();
            if (optionalResult.isPresent()) {
                return ResultMapper.INSTANCE.toDto(optionalResult.get());
            } else {
                throw new RuntimeException("Result not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void delete(Long resultId, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Result> optionalResult = user.getResults().stream()
                    .filter(result -> result.getId().equals(resultId))
                    .findFirst();
            if (optionalResult.isPresent()) {
                resultRepository.delete(optionalResult.get());
            } else {
                throw new RuntimeException("Result not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
