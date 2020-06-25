package com.portfolio.dictionary.repository;

import com.portfolio.dictionary.model.TestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestTypeRepository extends JpaRepository<TestType, Long> {
    Optional<TestType> findByTypeName(String name);
}
