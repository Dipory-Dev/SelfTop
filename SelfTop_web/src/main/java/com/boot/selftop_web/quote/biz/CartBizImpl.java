package com.boot.selftop_web.quote.biz;

import com.boot.selftop_web.quote.biz.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CartBizImpl {
	
	@Autowired
    private CartMapper cartMapper;


    public int saveQuote(Map<String, Object> param1, Map<String, Object> param2) {
        int res = 0;
        int member_no = (Integer) param1.get("member_no");
        String quoteName = (String) param1.get("quoteName");
        String assembly = param1.get("assemblyStatus").equals("조립 미신청") ? "N" : "Y";

        res = cartMapper.insertCart(member_no, quoteName, assembly);

        System.out.println("BIZ res : " + res);
        int quote_no = cartMapper.getCurrentQuoteNo();
        System.out.println("quote_no : " + quote_no);

        for (Map.Entry<String, Object> entry : param2.entrySet()) {

            // 각 카테고리의 정보를 Map으로 캐스팅
            Map<String, Object> categoryMap = (Map<String, Object>) entry.getValue();

            System.out.println("categoryMap : " + categoryMap);

            // product_code, amount, seller_no 추출 후 변환
            int product_code = Integer.parseInt((String) categoryMap.get("product_code"));
            System.out.println("product_code : " + product_code);
            int amount = (Integer) categoryMap.get("quantity");
            System.out.println("amount : " + amount);
            int seller_no = Integer.parseInt((String) categoryMap.get("seller_no"));
            System.out.println("seller_no : " + seller_no);

            // 각 카테고리의 정보를 테이블에 저장
            int temp = cartMapper.insertCartDetail(quote_no, member_no, product_code, seller_no, amount);
            res += temp;
        }
        return res;
    }


}
