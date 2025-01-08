package com.boot.selftop_web.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.selftop_web.order.biz.OrderBizImpl;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private OrderBizImpl orderBizImpl;

	@PostMapping("/order")
	public ResponseEntity<Map<String, Object>> receiveItems(@RequestBody Map<String, Object> items, HttpSession session) {

		Integer member_no = (Integer) session.getAttribute("member_no");

		Map<String, Object> param1 = new HashMap<>();
		Map<String, Object> param2 = new HashMap<>();
		param1.put("member_no", member_no);

		for(Map.Entry<String, Object> entry : items.entrySet()) {

			if (entry.getKey().equals("address") || entry.getKey().equals("request") || entry.getKey().equals("assemblyStatus")) {
				param1.put(entry.getKey(), entry.getValue());
			} else {
				param2.put(entry.getKey(), entry.getValue());
			}
		}

		System.out.println("param1: " + param1);
		System.out.println("param2: " + param2);
		orderBizImpl.saveOrder(param1, param2);

		Map<String, Object> result = new HashMap<>();

		if (member_no == null) {
			result.put("url", "/loginform");
			result.put("msg", "로그인이 필요한 서비스입니다.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}else {
			result.put("url", "/paysuccess");
			result.put("msg", "주문 내역이 저장되었습니다.");
			return ResponseEntity.ok(result);
		}
	}

}

