package com.portfolio.dictionary.repository;

import com.portfolio.dictionary.model.TestType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTypeRepository extends JpaRepository<TestType, Long> {
    TestType findByTypeName(String name);
}
