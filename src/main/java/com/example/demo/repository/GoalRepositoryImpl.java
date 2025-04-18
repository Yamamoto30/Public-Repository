package com.example.demo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Goal;

@Primary
@Repository
public class GoalRepositoryImpl implements GoalRepository {
    // メモリ内データストア（実際のアプリではデータベースを使用）
    private static final Map<Long, Goal> store = new HashMap<>();
    private static Long sequence = 1L;
    
    @Override
    public void save(Goal goal) {
        if (goal.getId() == null) {
            goal.setId(sequence++);
        }
        store.put(goal.getId(), goal);
    }
    
    @Override
    public List<Goal> findAll() {
        return new ArrayList<>(store.values());
    }
    
    @Override
    public Optional<Goal> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
    
    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
    
    @Override
    public Optional<Goal> findByUserId(String userId) {
        // ユーザーIDに一致する最初のGoalを返す
        return store.values().stream()
                .filter(goal -> goal.getUserId().equals(userId))
                .findFirst();
}
}
