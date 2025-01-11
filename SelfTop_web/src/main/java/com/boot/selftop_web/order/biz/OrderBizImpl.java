package com.boot.selftop_web.order.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.order.biz.mapper.OrderMapper;

@Service
public class OrderBizImpl {
	@Autowired
    private OrderMapper mapper;
	
	/*public int saveOrder(Map<String, Object> param1, Map<String, Object> param2) {
        int res = 0;
        int member_no = (Integer) param1.get("member_no");
        String address = (String) param1.get("address");
        String request = (String) param1.get("request");
        String assembly = param1.get("assemblyStatus").equals("조립 미신청") ? "N" : "Y";

        res = mapper.insertOrderBoard(member_no, address, request, assembly);
        
        System.out.println("BIZ res : " + res);
        int order_no = mapper.getCurrentOrderNo();
        System.out.println("order_no : " + order_no);

        // param2에서 여러 개의 주문 상세 정보를 처리하는 부분
        // param2에 여러 제품 정보가 포함되어 있다면, 이를 반복문으로 처리
        for (String key : param2.keySet()) {
            Map<String, Object> orderDetail = (Map<String, Object>) param2.get(key);
	        // param2에서 값 추출 후 Integer로 변환
	        int product_code = Integer.parseInt(param2.get("product_code").toString()); // String -> Integer 변환
	        int seller_no = Integer.parseInt(param2.get("seller_no").toString()); // String -> Integer 변환
	        int amount = Integer.parseInt(param2.get("amount").toString()); // String -> Integer 변환
	        int order_price = Integer.parseInt(param2.get("order_price").toString()); // String -> Integer 변환

	        System.out.println("product_code : " + product_code);
	        System.out.println("seller_no : " + seller_no);
	        System.out.println("amount : " + amount);
	        System.out.println("order_price : " + order_price);
	
	        // 각 카테고리의 정보를 테이블에 저장
	        int temp = mapper.insertOrderDetail(order_no, member_no, product_code, seller_no, amount, order_price);
	        res += temp;
        }

        return res;
	}*/
	public int saveOrder(Map<String, Object> param1, Object param2) {
	    int res = 0;
	    int member_no = (Integer) param1.get("member_no");
	    String address = (String) param1.get("address");
	    String request = (String) param1.get("request");
	    String assembly = param1.get("assemblyStatus").equals("조립 미신청") ? "N" : "Y";

	    // 주문 기본 정보 저장
	    res = mapper.insertOrderBoard(member_no, address, request, assembly);
	    System.out.println("BIZ res : " + res);

	    // 방금 저장한 주문 번호 가져오기
	    int order_no = mapper.getCurrentOrderNo();
	    System.out.println("order_no : " + order_no);

	    // param2가 단일 제품인지 여러 제품인지 확인
	    if (param2 instanceof Map) {
	        // 단일 제품 처리
	        Map<String, Object> orderDetail = (Map<String, Object>) param2;

	        int product_code = Integer.parseInt(orderDetail.get("product_code").toString());
	        int seller_no = Integer.parseInt(orderDetail.get("seller_no").toString());
	        int amount = Integer.parseInt(orderDetail.get("amount").toString());
	        int order_price = Integer.parseInt(orderDetail.get("order_price").toString());

	        System.out.println("product_code : " + product_code);
	        System.out.println("seller_no : " + seller_no);
	        System.out.println("amount : " + amount);
	        System.out.println("order_price : " + order_price);

	        // 주문 상세 정보 저장
	        res += mapper.insertOrderDetail(order_no, member_no, product_code, seller_no, amount, order_price);
	    } else if (param2 instanceof List) {
	        // 여러 제품 처리
	        List<Map<String, Object>> orderDetails = (List<Map<String, Object>>) param2;

	        for (Map<String, Object> orderDetail : orderDetails) {
	            int product_code = Integer.parseInt(orderDetail.get("product_code").toString());
	            int seller_no = Integer.parseInt(orderDetail.get("seller_no").toString());
	            int amount = Integer.parseInt(orderDetail.get("amount").toString());
	            int order_price = Integer.parseInt(orderDetail.get("order_price").toString());

	            System.out.println("product_code : " + product_code);
	            System.out.println("seller_no : " + seller_no);
	            System.out.println("amount : " + amount);
	            System.out.println("order_price : " + order_price);

	            // 주문 상세 정보 저장
	            res += mapper.insertOrderDetail(order_no, member_no, product_code, seller_no, amount, order_price);
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid param2 type. Must be Map or List.");
	    }

	    return res;
	}

}
