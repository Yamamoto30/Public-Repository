package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Goal;
import com.example.demo.repository.GoalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    @Override
    public void saveGoal(Goal goal) {
        goalRepository.save(goal);
    }

    @Override
    public Goal getGoalByUserId(String userId) {
        return goalRepository.findByUserId(userId).orElse(null);
    }
}

