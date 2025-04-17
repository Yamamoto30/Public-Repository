package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";  // home.htmlテンプレートを返す
    }
    
    // または既存のコントローラクラスに追加することもできます
    @GetMapping("/home")
    public String homePage() {
        return "home";  // home.htmlテンプレートを返す
    }
}
