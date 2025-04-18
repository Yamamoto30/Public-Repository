package com.example.demo.form;

import java.sql.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Data
public class WaterIntakeForm {
    
    // 編集時に必要なID
    private Long id;
    
    // ユーザーID（画面からは非表示）
    private String userId;
    
    @NotNull(message = "日付を入力してください。")
    @Past(message="今日以前の日付を入力してください。")
    private Date intakeDate;
    
    @NotNull(message="摂取量を入力してください。")
    @Min(value=10, message="10ml以上を入力してください。")
    @Max(value=5000, message="5000ml以下を入力してください。")
    private Integer amount;
}
