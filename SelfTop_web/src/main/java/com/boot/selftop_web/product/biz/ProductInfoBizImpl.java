package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import com.boot.selftop_web.product.model.dto.ProductInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoBizImpl {
    @Autowired
    private ProductMapper mapper;

    public ProductInfoDto selectOne(int product_code) {
        ProductInfoDto res = mapper.selectOne(product_code);
        return res;
    }
}
