// com.example.demo.controller.LoginController.java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.LoginForm;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    // ログイン画面表示
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm form, Model model, HttpSession session) {
        String password = UserRepository.findPassword(form.getUserId());
        if (password != null && password.equals(form.getPassword())) {
            session.setAttribute("loginUser", form.getUserId());
            return "redirect:/home";
        } else {
            model.addAttribute("loginForm", form);
            model.addAttribute("error", "ユーザーIDまたはパスワードが間違っています");
            return "login";
        }
    }

    // ログアウト
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
 // ユーザー登録画面表示
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "register";
    }

    // ユーザー登録処理
    @PostMapping("/register")
    public String register(@ModelAttribute LoginForm form, Model model) {
        if (UserRepository.exists(form.getUserId())) {
            model.addAttribute("error", "このユーザーIDはすでに使われています");
            model.addAttribute("loginForm", form);
            return "register";
        }

        UserRepository.save(form.getUserId(), form.getPassword());
        return "redirect:/login";
    }

}
