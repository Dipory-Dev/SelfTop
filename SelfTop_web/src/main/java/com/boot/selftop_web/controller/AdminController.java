package com.boot.selftop_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/main")
    public String main() {return "adminMain";}
    @GetMapping("/login")
    public String adminLogin() {
        return "adminLogin";
    }
    
}
