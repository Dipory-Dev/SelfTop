package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.ProductDto;

import java.util.List;

public interface ProductBiz<T extends ProductDto>{
    List<T> selectAll();
    T selectOne(int product_code);
    int insert(T dto);
}
