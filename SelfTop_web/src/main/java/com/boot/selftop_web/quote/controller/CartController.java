package com.boot.selftop_web.quote.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.selftop_web.quote.biz.CartBizImpl;
import com.boot.selftop_web.quote.model.dto.CartDTO;

@RestController
@RequestMapping("/api")
public class CartController {
	
	private final CartBizImpl cartBizImpl;
	
	public CartController(CartBizImpl cartBizImpl) {
		this.cartBizImpl = cartBizImpl;
	}
	
	
	@PostMapping("/save-cart")
    public String saveCart(@RequestBody CartDTO cartDTO) {
        // CartDTO 데이터를 저장
		cartBizImpl.saveCart(cartDTO);

        return "견적이 저장되었습니다!";
    }

}
