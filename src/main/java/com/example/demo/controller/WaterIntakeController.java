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

    // セッション属性名を一定に保つための定数
    private static final String USER_ID_SESSION_KEY = "loginUserId";

    /*--- 水分摂取入力画面の表示 ---*/
    @GetMapping("/show-water-form")
    public String showWaterForm(@ModelAttribute WaterIntakeForm form, HttpSession session) {
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        
        // ログインしていない場合
        if (loginUserId == null) {
            return "redirect:/login"; // ログイン画面にリダイレクト
        }
        // ユーザーIDはフォームに設定するがUIには表示しない
        form.setUserId(loginUserId);
        return "water-intake-form";
    }

    /*--- 水分摂取入力画面（確認画面からの戻り） ---*/
    @PostMapping("/show-water-form-ret")
    public String showWaterFormRet(@ModelAttribute WaterIntakeForm form, HttpSession session) {
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        form.setUserId(loginUserId);
        return "water-intake-form";
    }

    /*--- 水分摂取量の登録リクエスト ---*/
    @PostMapping("/water-intake-form")
    public String registWater(
            @Validated @ModelAttribute WaterIntakeForm form,
            BindingResult result,
            HttpSession session) {

        // セッションからユーザーIDを取得して設定
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        if (loginUserId == null) {
            return "redirect:/login";
        }
        form.setUserId(loginUserId);

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
            Model model,
            HttpSession session) {

        // 入力エラーがある場合は入力画面に戻す
        if (result.hasErrors()) {
            return "water-intake-form";
        }
        
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        if (loginUserId == null) {
            return "redirect:/login";
        }
        form.setUserId(loginUserId);

        WaterIntake waterIntake = new WaterIntake();
        waterIntake.setUserId(form.getUserId()); // ← 修正: ()を追加
        waterIntake.setIntakeDate(form.getIntakeDate());
        waterIntake.setAmount(form.getAmount());
        service.regist(waterIntake);

        model.addAttribute("msg", "水分摂取量の登録が完了しました。");

        return "complete-water-intake";
    }
    
    // 一覧表示
    @GetMapping("/water-intake-list")
    public String showWaterIntakeList(Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        
        // ログインしていない場合
        if (loginUserId == null) {
            return "redirect:/login";
        }

        List<WaterIntake> waterIntakeList = service.getWaterIntakesByUserId(loginUserId);
        model.addAttribute("waterIntakeList", waterIntakeList);

        Goal goal = goalService.getGoalByUserId(loginUserId);
        if (goal != null) {
            model.addAttribute("goalAmount", goal.getTargetAmount());
        } else {
            model.addAttribute("goalAmount", "未設定");
        }

        return "water-intake-list";
    }

    // 削除処理
    @PostMapping("/delete-water-intake")
    public String deleteWaterIntake(@RequestParam("id") Long id, Model model) {
        service.delete(id);
        model.addAttribute("msg","選択した水分摂取データを削除しました。");
        return "redirect:/water-intake-list";
    }
        
    // 編集画面の表示
    @GetMapping("/edit-water-intake")
    public String showEditForm(@RequestParam("id") Long id, Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        
        // ログインしていない場合
        if (loginUserId == null) {
            return "redirect:/login";
        }
        
        WaterIntake intake = service.findById(id);
        
        // セキュリティチェック: 他のユーザーのデータは編集できないようにする
        if (!loginUserId.equals(intake.getUserId())) {
            return "redirect:/water-intake-list";
        }

        // フォームに変換（編集画面でもWaterIntakeFormを使う場合）
        WaterIntakeForm form = new WaterIntakeForm();
        form.setId(intake.getId());
        form.setUserId(loginUserId); // ユーザーIDも設定するが表示はしない
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
            Model model,
            HttpSession session) {

        if (result.hasErrors()) {
            return "edit-water-intake-form";
        }
            
        String loginUserId = (String) session.getAttribute(USER_ID_SESSION_KEY);
        if (loginUserId == null) {
            return "redirect:/login";
        }

        WaterIntake intake = new WaterIntake();
        intake.setId(form.getId());
        intake.setUserId(loginUserId); // 修正: ()を削除しmethodにする
        intake.setIntakeDate(form.getIntakeDate());
        intake.setAmount(form.getAmount());

        service.update(intake);
        model.addAttribute("msg", "水分摂取データを更新しました。");
        return "redirect:/water-intake-list";
    }
}
