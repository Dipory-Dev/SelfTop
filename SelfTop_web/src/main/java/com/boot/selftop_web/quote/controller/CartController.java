package com.boot.selftop_web.quote.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.selftop_web.quote.biz.CartBizImpl;
import com.boot.selftop_web.quote.model.dto.CartDTO;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CartController {
	
	private final CartBizImpl cartBizImpl;
	
	public CartController(CartBizImpl cartBizImpl) {
		this.cartBizImpl = cartBizImpl;
	}
	
	
	@PostMapping("/save-cart")
    public String saveCart(@RequestBody Map cartDTO, HttpSession session) {
        // CartDTO 데이터를 저장

		Integer member_no = (Integer) session.getAttribute("member_no");
		System.out.println(member_no);

		if (member_no == null) {
			return "로그인이 필요한 서비스입니다.";
		}

        return "견적이 저장되었습니다!";
    }

}
