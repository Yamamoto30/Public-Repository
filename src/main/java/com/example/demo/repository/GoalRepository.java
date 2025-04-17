package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Goal;

public interface GoalRepository {
    void save(Goal goal);
    List<Goal> findAll();
    Optional<Goal> findById(Long id);
    void deleteById(Long id);
    Optional<Goal> findByUserId(String userId);  // 追加：ユーザーIDによる検索
    

}