package com.portfolio.dictionary.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Category")
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,
                mappedBy = "category")
    private Set<Word> words = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Result> results = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_test_type")
    private TestType testType;
}
