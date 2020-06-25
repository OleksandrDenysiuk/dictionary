package com.portfolio.dictionary.service;

import com.portfolio.dictionary.command.ResultCommand;
import com.portfolio.dictionary.dto.ResultDto;
import com.portfolio.dictionary.mapper.ResultMapper;
import com.portfolio.dictionary.model.*;
import com.portfolio.dictionary.repository.ResultRepository;
import com.portfolio.dictionary.repository.TestTypeRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Optional<TestType> optionalTestType = testTypeRepository.findByTypeName(resultCommand.getTestType());
            if (optionalTestType.isPresent()) {
                Result result = new Result();
                User user = userOptional.get();

                result.setStartTime(resultCommand.getStartTime());
                result.setFinishTime(resultCommand.getFinishTime());
                result.setTestType(optionalTestType.get());
                result.setUser(user);

                int points = resultCommand.getWords().size();

                for (String id : resultCommand.getCategoriesId()) {
                    Optional<Category> optionalCategory = user.getCategories().stream()
                            .filter(category -> category.getId().equals(Long.valueOf(id)))
                            .findFirst();

                    if (optionalCategory.isPresent()) {
                        Category category = optionalCategory.get();
                        result.getCategories().add(category);

                        points = getPoints(category, resultCommand.getWords(), resultCommand.getAnswers(), points);
                    }
                }
                result.setPoints(points);

                return ResultMapper.INSTANCE.toDto(resultRepository.save(result));
            } else {
                throw new RuntimeException("Test type not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private int getPoints(Category category, List<String> words,  List<String> answers, int points) {
        for (int i = 0; i < words.size(); i++) {
            final int k = i;
            Optional<Word> optionalWord = category.getWords().stream()
                    .filter(word -> word.getWord().equals(words.get(k)))
                    .findFirst();
            if (optionalWord.isPresent()) {
                if (!optionalWord.get().getTranslation().equals(answers.get(i))) {
                    points--;
                }
            }
        }
        return points;
    }

    @Override
    public ResultDto getOneByIdAndUserId(Long resultId, Long userId) {
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
                Result result = optionalResult.get();
                user.getResults().remove(result);
                result.setUser(null);
                resultRepository.delete(optionalResult.get());
            } else {
                throw new RuntimeException("Result not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
