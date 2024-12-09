package com.boot.selftop_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SellerController {

    @GetMapping("/sellerSignUp")
    public String showSignUpForm() {
        return "sellerSignUp";
    }
    
    @PostMapping("/sellerSignUp")
    public String registerSeller(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("email") String email,
            @RequestParam("terms") boolean terms,
            Model model) {

        // 비밀번호 확인
        if (!pw.equals(confirmPassword)) {
            model.addAttribute("error", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "sellerSignUp";
        }

        // 약관 동의 확인
        if (!terms) {
            model.addAttribute("error", "서비스 약관과 개인정보 처리방침에 동의하셔야 합니다.");
            return "sellerSignUp";
        }

        model.addAttribute("message", "회원가입이 완료되었습니다.");
        return "sellerMain";
    }
}

