package com.boot.selftop_web.product.biz;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductBizFactory implements InitializingBean {
    private Map<String, ProductBiz<?>> productBizMap = new HashMap<>();

    @Autowired
    @Qualifier("CPU")
    private ProductBiz<?> cpuBiz;

    // InitializingBean을 구현하여 의존성 주입 후 초기화 메서드를 실행
    @Override
    public void afterPropertiesSet() {
        productBizMap.put("cpu", cpuBiz);
        // 필요하다면 다른 카테고리에 대해서도 여기에 추가
    }

    public ProductBiz<?> getBiz(String category) {
        ProductBiz<?> biz = productBizMap.get(category.toLowerCase());
        if (biz == null) {
            System.err.println("No ProductBiz found for category: " + category);
        }
        return biz;
    }
}

