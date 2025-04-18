package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Goal;
import com.example.demo.entity.WaterIntake;
import com.example.demo.form.WaterIntakeForm;
import com.example.demo.service.GoalService;
import com.example.demo.service.WaterIntakeService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WaterIntakeController {

    private final WaterIntakeService service;
    private final GoalService goalService; 

    /*--- 水分摂取入力画面の表示 ---*/
    @GetMapping("/show-water-form")
    public String showWaterForm(@ModelAttribute WaterIntakeForm form) {
        return "water-intake-form";
    }

    /*--- 水分摂取入力画面（確認画面からの戻り） ---*/
    @PostMapping("/show-water-form-ret")
    public String showWaterFormRet(@ModelAttribute WaterIntakeForm form) {
        return "water-intake-form";
    }

    /*--- 水分摂取量の登録リクエスト ---*/
    @PostMapping("/water-intake-form")
    public String registWater(
            @Validated @ModelAttribute WaterIntakeForm form,
            BindingResult result) {

        // 入力エラーがある場合は入力画面に戻す
        if (result.hasErrors()) {
            return "water-intake-form";
        }

        // 正常な場合は確認画面へ
        return "confirm-water-intake";
    }

    /*--- 水分摂取量の登録確認リクエスト ---*/
    @PostMapping("/confirm-water-intake")
    public String confirmWaterIntake(
            @Validated WaterIntakeForm form,
            BindingResult result,
            Model model) {

        // 入力エラーがある場合は入力画面に戻す
        if (result.hasErrors()) {
            return "water-intake-form";
        }

        WaterIntake waterIntake = new WaterIntake();
        waterIntake.setUserId(form.getUserId());
        waterIntake.setIntakeDate(form.getIntakeDate());
        waterIntake.setAmount(form.getAmount());
        service.regist(waterIntake);

        model.addAttribute("msg", "水分摂取量の登録が完了しました。");

        return "complete-water-intake";
    }
    
 // 一覧表示
    @GetMapping("/water-intake-list")
    public String showWaterIntakeList(Model model, HttpSession session) {
        String loginUser = (String) session.getAttribute("loginUser");

        List<WaterIntake> waterIntakeList = service.getWaterIntakesByUserId(loginUser);
        model.addAttribute("waterIntakeList", waterIntakeList);

        Goal goal = goalService.getGoalByUserId(loginUser);
        if (goal != null) {
            model.addAttribute("goalAmount", goal.getTargetAmount());
        } else {
            model.addAttribute("goalAmount", "未設定");
        }

        return "water-intake-list";
    }

        // ⭐ 削除処理
        @PostMapping("/delete-water-intake")
        public String deleteWaterIntake(@RequestParam("id") Long id, Model model) {
        service.delete(id);
        model.addAttribute("msg","選択した水分摂取データを削除しました。");
        return "redirect:/water-intake-list";
        }
        
     // 編集画面の表示
        @GetMapping("/edit-water-intake")
        public String showEditForm(@RequestParam("id") Long id, Model model) {
            WaterIntake intake = service.findById(id);

            // フォームに変換（編集画面でもWaterIntakeFormを使う場合）
            WaterIntakeForm form = new WaterIntakeForm();
            form.setId(intake.getId());
            form.setUserId(intake.getUserId());
            form.setIntakeDate(intake.getIntakeDate());
            form.setAmount(intake.getAmount());

            model.addAttribute("form", form);
            return "edit-water-intake-form";
        }

        // 編集内容の更新
        @PostMapping("/update-water-intake")
        public String updateWaterIntake(
                @Validated @ModelAttribute("form") WaterIntakeForm form,
                BindingResult result,
                Model model) {

            if (result.hasErrors()) {
                return "edit-water-intake-form";
            }

            WaterIntake intake = new WaterIntake();
            intake.setId(form.getId());
            intake.setUserId(form.getUserId());
            intake.setIntakeDate(form.getIntakeDate());
            intake.setAmount(form.getAmount());

            service.update(intake);
            model.addAttribute("msg", "水分摂取データを更新しました。");
            return "redirect:/water-intake-list";
        }

}
