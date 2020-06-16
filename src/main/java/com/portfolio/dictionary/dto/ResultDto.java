package com.portfolio.dictionary.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDto {

    private Long id;

    private String testType;

    private String startTime;

    private String finishTime;

    private String points;

    private Set<CategoryDto> categoriesDto;
}
