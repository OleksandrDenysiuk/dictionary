package com.portfolio.dictionary.bootstrap;


import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.model.Word;
import com.portfolio.dictionary.repository.CategoryRepository;
import com.portfolio.dictionary.repository.RoleRepository;
import com.portfolio.dictionary.repository.UserRepository;
import com.portfolio.dictionary.repository.WordRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class UserInit implements ApplicationListener<ContextRefreshedEvent> {
    private final WordRepository wordRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder cryptPasswordEncoder;

    public UserInit(WordRepository wordRepository, CategoryRepository categoryRepository, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder cryptPasswordEncoder) {
        this.wordRepository = wordRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Category category = Category.builder()
                .id(1L)
                .name("Hello")
                .words(new HashSet<>())
                .build();

        Word word = new Word();
        word.setId(1L);
        word.setWord("word1");
        word.setTranslation("translation1");
        word.setCategory(category);

        category.getWords().add(word);

        User admin = User.builder()
                .id(1L)
                .username("admin")
                .email("admin@admin.com")
                .password(cryptPasswordEncoder.encode("1"))
                .active(true)
                .roles(Collections.singleton(roleRepository.findByName("ADMIN")))
                .categories(new HashSet<>())
                .build();

        admin.getCategories().add(category);

        category.setUser(admin);

        userRepository.save(admin);

        Word word2 = Word.builder()
                .id(2L)
                .word("word2")
                .translation("translation2")
                .category(category)
                .build();
        wordRepository.save(word2);

        Word word3 = Word.builder()
                .id(3L)
                .word("word3")
                .translation("translation3")
                .category(category)
                .build();
        wordRepository.save(word3);

        Category category2 = Category.builder()
                .id(2L)
                .name("Category2")
                .words(new HashSet<>())
                .user(admin)
                .build();

        categoryRepository.save(category2);

        Word word4 = Word.builder()
                .id(4L)
                .word("word4")
                .translation("translation4")
                .category(category2)
                .build();
        wordRepository.save(word4);

        Word word5 = Word.builder()
                .id(5L)
                .word("word5")
                .translation("translation5")
                .category(category2)
                .build();
        wordRepository.save(word5);


    }
}
