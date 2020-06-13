package com.portfolio.dictionary.model;

import com.portfolio.dictionary.dto.WordDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    WordDto word;
}
