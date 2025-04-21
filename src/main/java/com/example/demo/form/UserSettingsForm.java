package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSettingsForm {
    
    // 現在のユーザーID（変更前の値）
    private String currentUserId;
    
    // 新しいユーザーID
    @NotBlank(message = "新しいユーザーIDを入力してください")
    @Size(min = 4, max = 20, message = "ユーザーIDは4〜20文字で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "ユーザーIDは英数字、ハイフン、アンダースコアのみ使用可能です")
    private String newUserId;
    
    // 現在のパスワード（確認用）
    @NotBlank(message = "現在のパスワードを入力してください")
    private String currentPassword;
    
    // 新しいパスワード
    @NotBlank(message = "新しいパスワードを入力してください")
    @Size(min = 8, max = 64, message = "パスワードは8〜64文字で入力してください")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]+$", 
             message = "パスワードは英字と数字を含む必要があります")
    private String newPassword;
    
    // 新しいパスワード（確認用）
    @NotBlank(message = "パスワード（確認用）を入力してください")
    private String confirmNewPassword;
}
