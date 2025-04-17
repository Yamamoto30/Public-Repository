package com.example.demo.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GoalForm {

    @NotNull(message = "ユーザーIDを入力してください。")
    @Size(min = 4, max = 16, message = "ユーザーIDは4文字以上16文字以下で入力してください。")
    private String userId;

    @NotNull(message = "目標摂取量を入力してください。")
    @Min(value = 500, message = "500ml以上を入力してください。")
    @Max(value = 5000, message = "5000ml以下で入力してください。")
    private Integer targetAmount;
}
