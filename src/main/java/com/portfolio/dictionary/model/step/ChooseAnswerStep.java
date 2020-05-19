package com.portfolio.dictionary.model.step;

import com.portfolio.dictionary.model.Step;
import com.portfolio.dictionary.model.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChooseAnswerStep extends Step {
    private Word word;

    private List<String> variantOfAnswers = new ArrayList<>();
}
