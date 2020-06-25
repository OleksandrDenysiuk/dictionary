package com.portfolio.dictionary.command;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultCommand {

    private String testType;

    private String startTime;

    private String finishTime;

    private List<String> answers;

    private List<String> words;

    private List<String> categoriesId;

}
