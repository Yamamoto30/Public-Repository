package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.WaterIntake;

public interface WaterIntakeRepository {
    void save(WaterIntake waterIntake);
    List<WaterIntake> findAll();
    void deleteById(Long id);
    Optional<WaterIntake> findById(Long id);  // 追加
}

