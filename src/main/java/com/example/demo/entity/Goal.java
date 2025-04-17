package com.example.demo.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Goal {
    private Long id;
    private String userId;
    private Integer targetAmount;  // 追加: 目標摂取量
    private Integer dailyTarget;  // 1日の目標摂取量（ml）
    private Date startDate;       // 目標開始日
    private Date endDate;         // 目標終了日（オプション）
    private String description;   // 目標の説明（オプション）
}
