package com.portfolio.dictionary.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Result")
@Table(name = "result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_test_type")
    private TestType testType;

    private String startTime;

    private String finishTime;

    private int points;

    @ManyToMany(mappedBy = "results")
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;
}
