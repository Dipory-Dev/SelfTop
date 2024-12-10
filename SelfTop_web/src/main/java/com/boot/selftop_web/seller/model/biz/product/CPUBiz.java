package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.CPUDto;

import java.util.List;

public interface CPUBiz {
    public List<CPUDto> selectAll();
    public CPUDto selectOne(int product_code);
    public int insert(CPUDto dto);

}
