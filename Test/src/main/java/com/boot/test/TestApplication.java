package com.boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
    
    @RequestMapping("/text")
    public String Movetext() {
    	return "text";
    }
    
    @GetMapping("/test2")
    public String Movetest2() {
    	return "test2";
    }

}
