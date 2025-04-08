package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.WaterIntake;
import com.example.demo.form.WaterIntakeForm;
import com.example.demo.service.WaterIntakeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WaterIntakeController {

    private final WaterIntakeService service;

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
}
