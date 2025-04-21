// com.example.demo.controller.LoginController.java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            session.setAttribute("loginUserId", form.getUserId());
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
    
        
        @GetMapping("/settings")
        public String settingsForm() {
            return "settings";
            
        }
        @PostMapping("/settings")
        public String updateSettings(@RequestParam String oldPassword,
                                     @RequestParam String newUserId,
                                     @RequestParam String newPassword,
                                     HttpSession session,
                                     Model model) {
            // セッションからユーザーIDを取得（注意: セッション属性名は"loginUserId"）
            String currentUserId = (String) session.getAttribute("loginUserId");
            
            // ユーザーがログインしていないか、パスワードが間違っている場合
            if (currentUserId == null || !UserRepository.findPassword(currentUserId).equals(oldPassword)) {
                model.addAttribute("error", "現在のパスワードが正しくありません。");
                return "settings";
            }
            
            // ユーザー情報を更新
            boolean updated = UserRepository.updateUser(currentUserId, oldPassword, newUserId, newPassword);
            
            if (updated) {
                // 更新成功時はセッションも更新（loginUserIdという属性名を使用）
                session.setAttribute("loginUserId", newUserId);
                model.addAttribute("message", "ユーザーIDとパスワードを更新しました。");
            } else {
                model.addAttribute("error", "更新に失敗しました。");
            }
            return "settings";
        }
        
        
}

    
            
