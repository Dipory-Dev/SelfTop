package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.CoolerDto;
import com.boot.selftop_web.seller.model.mapper.CoolerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoolerBizImpl implements CoolerBiz{

    @Autowired
    private CoolerMapper coolerMapper;

    @Override
    public List<CoolerDto> selectAll() {
        return null;
    }

    @Override
    public CoolerDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(CoolerDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = coolerMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = coolerMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += coolerMapper.insertCooler(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }
}
