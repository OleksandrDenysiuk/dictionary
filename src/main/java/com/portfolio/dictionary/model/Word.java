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
@Entity(name = "Word")
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private String translation;

    @ManyToOne
    @JoinColumn(name = "fk_category")
    private Category category;

    @ManyToMany(mappedBy = "words")
    private Set<Test> tests = new HashSet<>();
}
