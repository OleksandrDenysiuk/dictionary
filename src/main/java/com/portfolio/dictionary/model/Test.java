package com.portfolio.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Test")
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_test_type")
    private TestType type;

    private String name;

    private String startTime;

    private String finishTime;

    private String result;

    @ManyToMany
    @JoinTable(name = "test_word",
            joinColumns = { @JoinColumn(name = "fk_test") },
            inverseJoinColumns = { @JoinColumn(name = "fk_word") })
    private Set<Word> words = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;
}
