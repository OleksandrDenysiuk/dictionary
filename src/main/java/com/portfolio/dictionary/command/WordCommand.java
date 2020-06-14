package com.portfolio.dictionary.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordCommand {
    private Long id;

    private String word;

    private String translation;

    private Long categoryId;
}
