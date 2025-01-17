package com.boot.selftop_web.order.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.selftop_web.member.customer.biz.CustomerBiz;
import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.order.biz.OrderBizImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderBizImpl orderBizImpl;
    @Autowired
	private CustomerBiz customerBiz;

    @GetMapping("/order")
    public ResponseEntity<Map<String, Object>> receiveItems(@RequestParam("orderData") String orderdata, HttpSession session) {
        Integer member_nocheck = (Integer) session.getAttribute("member_no");
        System.out.println(member_nocheck);
        Map<String, Object> param1 = new HashMap<>();
        List<Map<String, Object>> param2 = new ArrayList<>();
        // assemblyStatus 값을 저장할 리스트 생성
        List<String> assemblyStatusList = new ArrayList<>();

        try {
            // URL 디코딩
            String decodedata = URLDecoder.decode(orderdata, StandardCharsets.UTF_8.name());
            System.out.println("Decoded orderdata: " + decodedata);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(decodedata);
            int member_no = rootNode.get("memberno").asInt();

            // 단품 여부 확인
            boolean isSingleProduct = rootNode.has("product_code") && rootNode.has("seller_no") &&
                                       rootNode.has("amount") && rootNode.has("order_price");

            if (isSingleProduct) {
                // 단품 처리
                Map<String, Object> productInfo = new HashMap<>();
                productInfo.put("product_code", rootNode.get("product_code").asInt());
                productInfo.put("seller_no", rootNode.get("seller_no").asInt());
                productInfo.put("amount", rootNode.get("amount").asInt());
                productInfo.put("order_price", rootNode.get("order_price").asInt());
                param2.add(productInfo);

                // param1에 member_no와 address, request 추가
                param1.put("member_no", member_no);
                param1.put("address", rootNode.get("address").asText());
                param1.put("request", rootNode.get("request").asText());
                param1.put("assemblyStatus", 'N'); // 기본값 설정
            } else {
                // 다품 처리
                for (Iterator<String> it = rootNode.fieldNames(); it.hasNext(); ) {
                    String fieldName = it.next();
                    if (fieldName.startsWith("item")) { // item으로 시작하는 필드만 처리
                        JsonNode itemNode = rootNode.get(fieldName);
                        
                        // assemblyStatus가 존재하면 리스트에 추가
                        if (itemNode.hasNonNull("assemblyStatus")) {
                            String assemblyStatus = itemNode.get("assemblyStatus").asText();
                            assemblyStatusList.add(assemblyStatus); // 모든 item의 assemblyStatus를 리스트에 추가
                        }
                        
                        if (itemNode.hasNonNull("product_code") && itemNode.hasNonNull("seller_no") &&
                            itemNode.hasNonNull("amount") && itemNode.hasNonNull("order_price")) {
                            Map<String, Object> productInfo = new HashMap<>();
                            productInfo.put("product_code", itemNode.get("product_code").asInt());
                            productInfo.put("seller_no", itemNode.get("seller_no").asInt());
                            productInfo.put("amount", itemNode.get("amount").asInt());
                            productInfo.put("order_price", itemNode.get("order_price").asInt());
                            param2.add(productInfo);
                        } else {
                            System.out.println("Skipping invalid item: " + itemNode.toPrettyString());
                        }
                    }
                }

                // param1에 member_no와 address, request 추가
                param1.put("member_no", member_no);
                param1.put("address", rootNode.get("address").asText());
                param1.put("request", rootNode.get("request").asText());
                param1.put("assemblyStatus", assemblyStatusList);
            }

            // param1과 param2 출력
            System.out.println("param1: " + param1);
            System.out.println("param2: " + param2);

            // 주문 저장
            orderBizImpl.saveOrder(param1, param2);
            CustomerDto dto = customerBiz.selectCustomer(member_no);
            session.setAttribute("role", dto.getRole());
			session.setAttribute("member_no", dto.getMember_no());
			session.setAttribute("name", dto.getName());
            // 성공 메시지 반환
            Map<String, Object> result = new HashMap<>();

            // 로그인 체크
            if (member_no == 0) {
                result.put("url", "/loginform");
                result.put("msg", "로그인이 필요한 서비스입니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
            } else {
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
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/payfail").body(result);
        }
    }
}