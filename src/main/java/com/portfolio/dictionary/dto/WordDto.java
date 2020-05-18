package com.portfolio.dictionary.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordDto {

    private Long id;

    private String word;

    private String translation;

    private Long categoryId;
}
