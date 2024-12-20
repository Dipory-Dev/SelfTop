package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.HDDDto;
import com.boot.selftop_web.product.biz.mapper.HDDMapper;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("HDD")
public class HDDBizImpl implements ProductBiz<HDDDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private HDDMapper hddMapper;

    @Override
    public List<HDDDto> selectAll() {
        return null;
    }

    @Override
    public HDDDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(HDDDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += hddMapper.insertHDD(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }
}
