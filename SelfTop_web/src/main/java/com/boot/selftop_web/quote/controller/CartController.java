package com.boot.selftop_web.quote.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController {
	
	
	@PostMapping("/items")
	public Map<String, Object> receiveItems(@RequestBody Map<String, Object> items) {
        
        return items;
    }

}
