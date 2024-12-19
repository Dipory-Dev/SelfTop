package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.PowerDto;
import com.boot.selftop_web.product.biz.mapper.PowerMapper;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("파워")
public class PowerBizImpl implements ProductBiz<PowerDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PowerMapper powerMapper;

    @Override
    public List<PowerDto> selectAll() {
        return null;
    }

    @Override
    public PowerDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(PowerDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += powerMapper.insertPower(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }
}
