package com.boot.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
	
	@GetMapping("/test2")
	public String main() {
		
		return "test2";
		
	}
	
	
}
