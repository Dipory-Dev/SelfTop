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
	
	public int saveOrder(Map<String, Object> param1, Map<String, Object> param2) {
        int res = 0;
        int member_no = (Integer) param1.get("member_no");
        String address = (String) param1.get("address");
        String request = (String) param1.get("request");
        String assembly = param1.get("assemblyStatus").equals("조립 미신청") ? "N" : "Y";

        res = mapper.insertOrderBoard(member_no, address, request, assembly);

        System.out.println("BIZ res : " + res);
        int order_no = mapper.getCurrentOrderNo();
        System.out.println("order_no : " + order_no);

        for (Map.Entry<String, Object> entry : param2.entrySet()) {

            // 각 카테고리의 정보를 Map으로 캐스팅
            Map<String, Object> categoryMap = (Map<String, Object>) entry.getValue();

            System.out.println("categoryMap : " + categoryMap);

            // product_code, seller_no, amount, order_price 추출 후 변환
            int product_code = Integer.parseInt((String) categoryMap.get("product_code"));
            System.out.println("product_code : " + product_code);
            int seller_no = Integer.parseInt((String) categoryMap.get("seller_no"));
            System.out.println("seller_no : " + seller_no);
            int amount = (Integer) categoryMap.get("quantity");
            System.out.println("amount : " + amount);
            int order_price = (Integer) categoryMap.get("order_price");
            System.out.println("order_price : " + order_price);

            // 각 카테고리의 정보를 테이블에 저장
            int temp = mapper.insertOrderDetail(order_no, member_no, product_code, seller_no, amount, order_price);
            res += temp;
            System.out.println("res: " + res);
            
        }
        return res;
    }
}
