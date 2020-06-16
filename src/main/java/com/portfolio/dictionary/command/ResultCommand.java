package com.portfolio.dictionary.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultCommand {

    private String testType;

    private String startTime;

    private String finishTime;

    private List<String> answers;

    private List<String> words;

    private List<String> categoriesId;

}
