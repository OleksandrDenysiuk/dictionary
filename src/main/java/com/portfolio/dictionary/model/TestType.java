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
@Entity(name = "TestType")
@Table(name = "test_type")
public class TestType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeName;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "testType")
    private Set<Category> category = new HashSet<>();
}
