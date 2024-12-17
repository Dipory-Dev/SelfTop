package com.boot.selftop_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CustomerController {

	@GetMapping("/main")
	public String SelfTopMain() {
		return "mainPage";
	}
}
