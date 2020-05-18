package com.portfolio.dictionary.bootstrap;


import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.model.Word;
import com.portfolio.dictionary.repository.RoleRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class UserInit implements ApplicationListener<ContextRefreshedEvent> {
    private  final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder cryptPasswordEncoder;

    public UserInit(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder cryptPasswordEncoder) {
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
    }
}
