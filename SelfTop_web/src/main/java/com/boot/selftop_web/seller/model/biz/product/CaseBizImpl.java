package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.CaseDto;
import com.boot.selftop_web.seller.model.mapper.product.CaseMapper;
import com.boot.selftop_web.seller.model.mapper.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("케이스")
public class CaseBizImpl implements ProductBiz<CaseDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CaseMapper caseMapper;
    @Override
    public List<CaseDto> selectAll() {
        return null;
    }

    @Override
    public CaseDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(CaseDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += caseMapper.insertCase_board(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }
}
