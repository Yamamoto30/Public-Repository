package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Goal;
import com.example.demo.form.GoalForm;
import com.example.demo.service.GoalService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;
    
    // セッション属性名を一定に保つための定数
    private static final String USER_ID_SESSION_KEY = "loginUserId";
    
    // 入力画面表示
    @GetMapping("/show-goal-form")
    public String showGoalForm(@ModelAttribute GoalForm form, HttpSession session) {
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        
        // ログインしていない場合
        if (loginUserId == null) {
            return "redirect:/login"; // ログイン画面にリダイレクト
        }
        
        // ユーザーIDをフォームに設定
        form.setUserId(loginUserId);
        
        return "goal-form";
    }
    
    // 確認画面表示
    @PostMapping("/confirm-goal")
    public String confirmGoal(
            @Validated @ModelAttribute GoalForm form,
            BindingResult result,
            Model model,
            HttpSession session) {
        
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        if (loginUserId == null) {
            return "redirect:/login";
        }
        
        // ユーザーIDをフォームに設定
        form.setUserId(loginUserId);
        
        if (result.hasErrors()) {
            return "goal-form";
        }
        
        model.addAttribute("goalForm", form);
        return "confirm-goal";
    }
    
    // 確認画面からの戻る処理（目標設定フォームに戻る）
    @PostMapping("/show-goal-form-ret")
    public String showGoalFormFromConfirmation(@ModelAttribute GoalForm form, HttpSession session) {
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        form.setUserId(loginUserId);
        return "goal-form"; // 再度フォームを表示
    }
    
    // 登録処理 → 完了画面へ
    @PostMapping("/complete-goal")
    public String completeGoal(
            @ModelAttribute GoalForm form,
            Model model,
            HttpSession session) {
            
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        if (loginUserId == null) {
            return "redirect:/login";
        }
        
        // ユーザーIDをフォームにセット（念のため）
        form.setUserId(loginUserId);
        
        Goal goal = new Goal();
        goal.setUserId(loginUserId); // セッションから取得したユーザーIDを使用
        goal.setTargetAmount(form.getTargetAmount());
        
        goalService.saveGoal(goal);
        
        model.addAttribute("msg", "目標摂取量を登録しました！");
        return "complete-goal";
    }
}
