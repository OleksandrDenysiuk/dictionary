package com.portfolio.dictionary.service;

import com.portfolio.dictionary.model.TestType;
import com.portfolio.dictionary.model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestServiceImplTest {

    TestService testService;

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl();
    }

    @Test
    void generate() {
        Set<Word> words = new HashSet<>();
        words.add(Word.builder().id(1L).word("word1").translation("trans1").build());
        words.add(Word.builder().id(2L).word("word2").translation("trans2").build());
        words.add(Word.builder().id(3L).word("word3").translation("trans3").build());
        words.add(Word.builder().id(4L).word("word4").translation("trans4").build());
        words.add(Word.builder().id(5L).word("word5").translation("trans5").build());
        words.add(Word.builder().id(6L).word("word6").translation("trans6").build());
        words.add(Word.builder().id(7L).word("word7").translation("trans7").build());
        words.add(Word.builder().id(8L).word("word8").translation("trans8").build());
        words.add(Word.builder().id(9L).word("word9").translation("trans9").build());
        words.add(Word.builder().id(10L).word("word10").translation("trans10").build());

        com.portfolio.dictionary.model.Test test = testService.generate(new TestType(1L , "CHOOSE_CORRECT"), 1L, words);

        assertNotNull(test);
        assertEquals(words.size(),test.getSteps().size());
    }
}