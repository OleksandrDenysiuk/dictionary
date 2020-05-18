package com.portfolio.dictionary.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    private Long userId;

    private Set<WordDto> words;
}
