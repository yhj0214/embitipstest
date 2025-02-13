package com.example.embitipstest.service;

import com.example.embitipstest.repository.TestRepository;
import com.example.embitipstest.repository.entity.TestEntity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    @Transactional
    public TestEntity saveEntity(String name){
        return testRepository.save(new TestEntity(name));
    }
}
