package com.portfolio.dictionary.model.question;

import com.portfolio.dictionary.model.Question;
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
public class ChooseAnswer extends Question {
    private List<String> variantOfAnswers = new ArrayList<>();
}
