package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Question;
import com.portfolio.dictionary.model.Test;
import com.portfolio.dictionary.model.TestType;
import com.portfolio.dictionary.model.question.ChooseAnswer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public Test generate(TestType testType, Set<CategoryDto> categoryDtoSet) {
        Test test = new Test();
        test.setType(testType.getTypeName());

        if (testType.getTypeName().equals("CHOOSE_CORRECT")) {
            test.setQuestions(chooseTranslate(categoryDtoSet));
        } else {
            test.setQuestions(writeTranslate(categoryDtoSet));
        }
        return test;
    }

    private List<ChooseAnswer> chooseTranslate(Set<CategoryDto> categoryDtoSet) {
        List<ChooseAnswer> steps = new ArrayList<>();
        Random random = new Random();
        Set<WordDto> wordDtoSet = new HashSet<>();

        for(CategoryDto categoryDto : categoryDtoSet) {
            wordDtoSet.addAll(categoryDto.getWords());
        }
        int amountAnswers = 4;

        for (WordDto word : wordDtoSet) {
            ChooseAnswer step = new ChooseAnswer();
            step.setWord(word);
            step.getVariantOfAnswers().add(word.getTranslation());
            List<WordDto> variants = new ArrayList<>(wordDtoSet);
            variants.remove(word);
            for (int i = 0; i < amountAnswers - 1; i++) {
                Collections.shuffle(variants);
                int randomIndex = random.nextInt(variants.size());
                if(randomIndex == variants.size()){
                    randomIndex--;
                }
                WordDto oneOfVariants = variants.get(randomIndex);
                step.getVariantOfAnswers().add(oneOfVariants.getTranslation());
                variants.remove(oneOfVariants);
            }
            Collections.shuffle(step.getVariantOfAnswers());
            steps.add(step);
        }

        return steps;
    }

    private List<Question> writeTranslate(Set<CategoryDto> categoryDtoSet) {
        List<Question> questions = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtoSet) {
            Set<WordDto> wordDtoSet = categoryDto.getWords();
            for (WordDto word : wordDtoSet) {
                Question question = new Question();
                question.setWord(word);
                questions.add(question);
            }
        }
        Collections.shuffle(questions);
        return questions;
    }
}
