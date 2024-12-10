package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.CaseDto;

import java.util.List;

public interface CaseBiz {
    public List<CaseDto> selectAll();
    public CaseDto selectOne(int product_code);
    public int insert(CaseDto dto);

}
