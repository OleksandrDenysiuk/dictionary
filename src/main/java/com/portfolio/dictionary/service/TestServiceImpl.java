package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.Test;
import com.portfolio.dictionary.model.TestType;
import com.portfolio.dictionary.model.Word;
import com.portfolio.dictionary.model.step.ChooseAnswerStep;
import com.portfolio.dictionary.model.step.WriteAnswerStep;

import java.util.*;

public class TestServiceImpl implements TestService {

    @Override
    public Test generate(TestType testType, Long userId, Set<Word> wordSet) {
        Test test = new Test();
        test.setType(testType);

        if(testType.getTypeName().equals("CHOOSE_CORRECT")){
            test.setSteps(chooseTranslate(wordSet));
        }else {
            test.setSteps(writeTranslate(wordSet));
        }

        return test;
    }

    private List<ChooseAnswerStep> chooseTranslate(Set<Word> wordSet){
        List<ChooseAnswerStep> steps = new ArrayList<>();
        Random random = new Random();
        int randomIndex = random.nextInt(wordSet.size());
        int amountAnswers = 4;

        for(Word word : wordSet){
            ChooseAnswerStep step = new ChooseAnswerStep();
            step.setWord(word);
            step.getVariantOfAnswers().add(word.getTranslation());
            List<Word> variants = new ArrayList<>(wordSet);
            variants.remove(word);
            for(int i=0; i < amountAnswers - 1; i++){
                Collections.shuffle(variants);
                Word oneOfVariants = variants.get(randomIndex - 1);
                step.getVariantOfAnswers().add(oneOfVariants.getTranslation());
                variants.remove(oneOfVariants);
            }
            Collections.shuffle(step.getVariantOfAnswers());
            steps.add(step);
        }

        return steps;
    }

    private List<WriteAnswerStep> writeTranslate(Set<Word> wordSet){
        List<WriteAnswerStep> steps = new ArrayList<>();
        for(Word word : wordSet){
            WriteAnswerStep step = new WriteAnswerStep();
            step.setWord(word);
            steps.add(step);
        }
        Collections.shuffle(steps);
        return steps;
    }
}
