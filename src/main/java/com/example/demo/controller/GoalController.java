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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    // 入力画面表示
    @GetMapping("/show-goal-form")
    public String showGoalForm(@ModelAttribute GoalForm form) {
        return "goal-form";
    }

    // 確認画面表示
    @PostMapping("/confirm-goal")
    public String confirmGoal(@Validated @ModelAttribute GoalForm form,BindingResult result,Model model) {
         if (result.hasErrors()) {
            return "goal-form";
        }

        model.addAttribute("goalForm", form);
        return "confirm-goal";
    }
    
     // 確認画面からの戻る処理（目標設定フォームに戻る）
    @PostMapping("/show-goal-form-ret")
    public String showGoalFormFromConfirmation(@ModelAttribute GoalForm form) {
        return "goal-form"; // 再度フォームを表示
    }

    // 登録処理 → 完了画面へ
    @PostMapping("/complete-goal")
    public String completeGoal(@ModelAttribute GoalForm form, Model model) {

        Goal goal = new Goal();
        goal.setUserId(form.getUserId());
        goal.setTargetAmount(form.getTargetAmount());

        goalService.saveGoal(goal);

        model.addAttribute("msg", "目標摂取量を登録しました！");
        return "complete-goal";
    }
    
 
}
