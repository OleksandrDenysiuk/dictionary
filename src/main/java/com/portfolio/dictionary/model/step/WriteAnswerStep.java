package com.portfolio.dictionary.model.step;

import com.portfolio.dictionary.model.Step;
import com.portfolio.dictionary.model.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WriteAnswerStep extends Step {
    private Word word;
}
