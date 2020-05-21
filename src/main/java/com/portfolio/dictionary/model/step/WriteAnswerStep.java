package com.portfolio.dictionary.model.step;

import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WriteAnswerStep extends Step {
    private WordDto word;

    private String answer;
}
