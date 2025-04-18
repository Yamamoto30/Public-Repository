package com.example.demo.form;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoalForm {
   
    // ユーザーID（画面からは非表示）
    private String userId;
    
    @NotNull(message = "目標摂取量を入力してください。")
    @Min(value = 500, message = "500ml以上を入力してください。")
    @Max(value = 5000, message = "5000ml以下で入力してください。")
    private Integer targetAmount;
}
