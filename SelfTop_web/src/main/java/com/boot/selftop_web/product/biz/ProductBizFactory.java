package com.boot.selftop_web.product.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductBizFactory {
    @Autowired
    private Map<String, ? extends ProductBiz> productBizMap;

    public ProductBiz<?> getBiz(String category) {
        return productBizMap.get(category);
    }
}
