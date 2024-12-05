package com.boot.test.controller;


@Controller
public class TestController {

    @GetMapping("/test1")
    public String test1() {
        return "text";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/test3")
    public String test3() {
        return "test3";
    }
    
    @GetMapping("/test4")
    public String test4() {
        return "test4";
    }
}
