package com.boot.selftop_web.order.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.selftop_web.order.biz.OrderBizImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private OrderBizImpl orderBizImpl;

	@GetMapping("/order")
	public ResponseEntity<Map<String, Object>> receiveItems(@RequestParam("orderdata") String orderdata, HttpSession session) {

	    Integer member_no = (Integer) session.getAttribute("member_no");

	    Map<String, Object> param1 = new HashMap<>();
	    Map<String, Object> param2 = new HashMap<>();

	    try {
	        // URL 디코딩
	        String decodedata = URLDecoder.decode(orderdata, StandardCharsets.UTF_8.name());
	        System.out.println("Decoded orderdata: " + decodedata);

	        // JSON 파싱
	        ObjectMapper objectMapper = new ObjectMapper();
	        Map<String, Object> items = objectMapper.readValue(decodedata, Map.class);

	        // param1에 member_no와 address, request, assemblyStatus 추가
	        param1.put("member_no", member_no);

	        for (Map.Entry<String, Object> entry : items.entrySet()) {
	            if (entry.getKey().equals("address") || entry.getKey().equals("request") || entry.getKey().equals("assemblyStatus")) {
	                param1.put(entry.getKey(), entry.getValue());
	            }else {
	                param2.put(entry.getKey(), entry.getValue());
	            }
	        }

	        // param1과 param2 출력
	        System.out.println("param1: " + param1);
	        System.out.println("param2: " + param2);

	        // 주문 저장
	        orderBizImpl.saveOrder(param1, param2);

	        // 성공 메시지 반환
	        Map<String, Object> result = new HashMap<>();

	        // 로그인 체크
		    if (member_no == null) {
		        result.put("url", "/loginform");
		        result.put("msg", "로그인이 필요한 서비스입니다.");
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		    }else {
		    	result.put("msg", "주문 내역이 저장되었습니다.");
		    	// 주문 성공 후 paysuccess 페이지로 리디렉션
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/paysuccess").body(result);
		    }

	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        Map<String, Object> result = new HashMap<>();
	        result.put("msg", "데이터 디코딩 오류가 발생했습니다.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	    } catch (Exception e) {
	        e.printStackTrace();
	        Map<String, Object> result = new HashMap<>();
	        result.put("msg", "주문 처리 중 오류가 발생했습니다.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
	    }
	}

}

