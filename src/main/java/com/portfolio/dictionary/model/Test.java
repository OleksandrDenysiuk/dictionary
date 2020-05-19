package com.portfolio.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    private TestType type;

    private String result;

    private List<? extends Step> steps = new ArrayList<>();
}
