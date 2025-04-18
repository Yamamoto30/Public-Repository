package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Goal;

@Repository
public class InMemoryGoalRepository implements GoalRepository {

    private static List<Goal> goals = new ArrayList<>();

    @Override
    public void save(Goal goal) {
        goals.removeIf(g -> g.getUserId().equals(goal.getUserId())); // ユーザーごとに1つの目標を想定
        goals.add(goal);
    }

    @Override
    public List<Goal> findAll() {
        return new ArrayList<>(goals);
    }

    @Override
    public Optional<Goal> findById(Long id) {
        return goals.stream().filter(g -> g.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        goals.removeIf(g -> g.getId().equals(id));
    }

    @Override
    public Optional<Goal> findByUserId(String userId) {
        return goals.stream().filter(g -> g.getUserId().equals(userId)).findFirst();
    }
}
