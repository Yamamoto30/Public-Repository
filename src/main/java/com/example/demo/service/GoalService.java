package com.example.demo.service;

import com.example.demo.entity.Goal;

public interface GoalService {

    // 目標を登録または更新
    void saveGoal(Goal goal);

    // ユーザーIDに基づいて目標を取得
    Goal getGoalByUserId(String userId);

   

}

