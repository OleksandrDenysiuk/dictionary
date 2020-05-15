package com.portfolio.dictionary.bootstrap;

import com.portfolio.dictionary.model.Role;
import com.portfolio.dictionary.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RoleInit implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleInit(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Role user = new Role();
        user.setName("USER");
        roleRepository.save(user);

        Role admin = new Role();
        admin.setName("ADMIN");
        roleRepository.save(admin);
    }
}
