package com.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("/test4")
    public String myPage(Model model) {
        model.addAttribute("name", "이창진");
        model.addAttribute("id", "lcj123");
        model.addAttribute("phone", "010-1234-5678");
        String email = "leechangin@example.com";
        String emailFront = email.split("@")[0];
        String emailBack = email.split("@")[1];
        model.addAttribute("emailFront", emailFront);
        model.addAttribute("emailBack", emailBack);
        
        return "test4";
    }
}
