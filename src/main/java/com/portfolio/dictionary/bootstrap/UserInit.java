package com.portfolio.dictionary.bootstrap;


import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.RoleRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

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
                .build();

        User admin = User.builder()
                .id(1L)
                .username("admin")
                .email("admin@admin.com")
                .password(cryptPasswordEncoder.encode("1"))
                .active(true)
                .roles(Collections.singleton(roleRepository.findByName("ADMIN")))
                .categories(Collections.singleton(category))
                .build();

        category.setUser(admin);

        userRepository.save(admin);
    }
}
