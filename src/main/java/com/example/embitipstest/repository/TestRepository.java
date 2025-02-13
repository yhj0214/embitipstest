package com.example.embitipstest.repository;

import com.example.embitipstest.repository.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
