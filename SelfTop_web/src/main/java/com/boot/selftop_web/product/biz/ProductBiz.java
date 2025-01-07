package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.ProductDto;

import java.util.List;
import java.util.Map;

public interface ProductBiz<T extends ProductDto>{
    List<T> selectAll();
    T selectOne(int product_code);
    int insert(T dto);
    
    //main에서 부품 선택시 카테고리로 설정
    List<T> getProductsByCategory(String category, String sort, String searchTerm);
    
    // 필터링을 위한 메소드 추가
    List<T> filterProducts(Map<String, List<String>> filters, String sort);
}
