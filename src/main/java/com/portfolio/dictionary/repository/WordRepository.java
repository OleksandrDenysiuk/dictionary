package com.portfolio.dictionary.repository;

import com.portfolio.dictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
