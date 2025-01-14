package com.boot.selftop_web.quote.biz;

import com.boot.selftop_web.quote.biz.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartBizImpl {
	
	@Autowired
    private CartMapper cartMapper;


	public int saveQuote(Map<String, Object> param1, Map<String, Object> param2) {
        int res = 0;

        // 기본 정보 추출
        int member_no = (Integer) param1.get("member_no");
        String quoteName = (String) param1.get("quoteName");
        String assembly = param1.get("assemblyStatus").equals("조립 미신청") ? "N" : "Y";

        // QUOTE 테이블에 저장
        res = cartMapper.insertCart(member_no, quoteName, assembly);
        System.out.println("BIZ res : " + res);

        // 현재 생성된 quote_no 가져오기
        int quote_no = cartMapper.getCurrentQuoteNo();
        System.out.println("quote_no : " + quote_no);

        // param2에서 items를 추출
        List<Map<String, Object>> items = (List<Map<String, Object>>) param2.get("items");

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("저장할 아이템 목록이 없습니다.");
        }

        for (Map<String, Object> item : items) {
            // 각 아이템에서 필요한 데이터 추출
            int product_code = Integer.parseInt(item.get("product_code").toString());
            int seller_no = Integer.parseInt(item.get("seller_no").toString());
            int quantity = Integer.parseInt(item.get("quantity").toString());

            System.out.println("product_code : " + product_code);
            System.out.println("quantity : " + quantity);
            System.out.println("seller_no : " + seller_no);

            // QUOTE_DETAIL 테이블에 저장
            int temp = cartMapper.insertCartDetail(quote_no, member_no, product_code, seller_no, quantity);
            res += temp;
        }

        return res;
    }
}
