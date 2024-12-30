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
    
    @Autowired
    @Qualifier("쿨러")
    private ProductBiz<?> coolerBiz;
    
    @Autowired
    @Qualifier("케이스")
    private ProductBiz<?> caseBiz;
    
    @Autowired
    @Qualifier("그래픽카드")
    private ProductBiz<?> gpuBiz;
    
    @Autowired
    @Qualifier("HDD")
    private ProductBiz<?> hddBiz;
    
    @Autowired
    @Qualifier("파워")
    private ProductBiz<?> powerBiz;
    
    @Autowired
    @Qualifier("RAM")
    private ProductBiz<?> ramBiz;
    
    @Autowired
    @Qualifier("SSD")
    private ProductBiz<?> ssdBiz;
    
    @Autowired
    @Qualifier("메인보드")
    private ProductBiz<?> mainboardBiz;

    // InitializingBean을 구현하여 의존성 주입 후 초기화 메서드를 실행
    @Override
    public void afterPropertiesSet() {
        productBizMap.put("cpu", cpuBiz);
        productBizMap.put("쿨러", coolerBiz);
        productBizMap.put("케이스", caseBiz);
        productBizMap.put("그래픽카드", gpuBiz);
        productBizMap.put("hdd", hddBiz);
        productBizMap.put("파워", powerBiz);
        productBizMap.put("ram", ramBiz);
        productBizMap.put("ssd", ssdBiz);
        productBizMap.put("메인보드", mainboardBiz);
    }

    public ProductBiz<?> getBiz(String category) {
        ProductBiz<?> biz = productBizMap.get(category.toLowerCase());
        if (biz == null) {
            System.err.println("No ProductBiz found for category: " + category);
        }
        return biz;
    }
}

