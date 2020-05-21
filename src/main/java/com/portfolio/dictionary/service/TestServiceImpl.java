package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Test;
import com.portfolio.dictionary.model.TestType;
import com.portfolio.dictionary.model.step.ChooseAnswerStep;
import com.portfolio.dictionary.model.step.WriteAnswerStep;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public Test generate(TestType testType, Set<CategoryDto> categoryDtoSet) {
        Test test = new Test();
        test.setType(testType.getTypeName());

        if (testType.getTypeName().equals("CHOOSE_CORRECT")) {
            test.setSteps(chooseTranslate(categoryDtoSet));
        } else {
            test.setSteps(writeTranslate(categoryDtoSet));
        }

        return test;
    }

    private List<ChooseAnswerStep> chooseTranslate(Set<CategoryDto> categoryDtoSet) {
        List<ChooseAnswerStep> steps = new ArrayList<>();
        Random random = new Random();
        Set<WordDto> wordDtoSet = new HashSet<>();

        for(CategoryDto categoryDto : categoryDtoSet) {
            wordDtoSet.addAll(categoryDto.getWords());
        }
        int amountAnswers = 4;

        for (WordDto word : wordDtoSet) {
            ChooseAnswerStep step = new ChooseAnswerStep();
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

    private List<WriteAnswerStep> writeTranslate(Set<CategoryDto> categoryDtoSet) {
        List<WriteAnswerStep> steps = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtoSet) {
            Set<WordDto> wordDtoSet = categoryDto.getWords();
            for (WordDto word : wordDtoSet) {
                WriteAnswerStep step = new WriteAnswerStep();
                step.setWord(word);
                steps.add(step);
            }
        }
        Collections.shuffle(steps);
        return steps;
    }
}
