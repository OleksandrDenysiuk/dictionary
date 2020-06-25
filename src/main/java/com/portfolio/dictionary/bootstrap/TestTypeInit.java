package com.portfolio.dictionary.bootstrap;

import com.portfolio.dictionary.model.TestType;
import com.portfolio.dictionary.repository.TestTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class TestTypeInit implements ApplicationListener<ContextRefreshedEvent> {

    private final TestTypeRepository testTypeRepository;

    public TestTypeInit(TestTypeRepository testTypeRepository) {
        this.testTypeRepository = testTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        TestType type = new TestType();
        type.setTypeName("WRITE_ANSWER");
        testTypeRepository.save(type);
    }
}
