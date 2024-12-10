package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.CaseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseBizImpl implements CaseBiz{
    @Override
    public List<CaseDto> selectAll() {
        return null;
    }

    @Override
    public CaseDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(CaseDto dto) {
        return 0;
    }
}
