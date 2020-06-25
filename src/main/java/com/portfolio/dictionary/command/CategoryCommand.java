package com.portfolio.dictionary.command;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCommand {

    Long id;

    String categoryName;
}
