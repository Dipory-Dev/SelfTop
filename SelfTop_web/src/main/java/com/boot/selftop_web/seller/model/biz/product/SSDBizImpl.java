package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.SSDDto;
import com.boot.selftop_web.seller.model.mapper.product.ProductMapper;
import com.boot.selftop_web.seller.model.mapper.product.SSDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("SSD")
public class SSDBizImpl implements ProductBiz<SSDDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SSDMapper ssdMapper;

    @Override
    public List<SSDDto> selectAll() {
        return null;
    }

    @Override
    public SSDDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(SSDDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += ssdMapper.insertSSD(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }
}
