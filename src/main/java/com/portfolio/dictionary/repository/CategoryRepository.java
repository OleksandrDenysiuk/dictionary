package com.portfolio.dictionary.repository;

import com.portfolio.dictionary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByIdAndUserId(Long categoryId, Long userId);
}
