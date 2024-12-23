package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.CPUDto;
import com.boot.selftop_web.product.biz.mapper.CPUMapper;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CPU")
public class CPUBizImpl implements ProductBiz<CPUDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CPUMapper cpuMapper;

    @Override
    public List<CPUDto> selectAll() {
        return null;
    }

    @Override
    public CPUDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(CPUDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += cpuMapper.insertCPU(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }
}
