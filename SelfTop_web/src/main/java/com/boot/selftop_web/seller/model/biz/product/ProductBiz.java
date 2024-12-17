package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.ProductDto;

import java.util.List;

public interface ProductBiz<T extends ProductDto>{
    List<T> selectAll();
    T selectOne(int product_code);
    int insert(T dto);
}
