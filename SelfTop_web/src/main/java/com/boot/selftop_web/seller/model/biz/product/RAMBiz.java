package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.RAMDto;

import java.util.List;

public interface RAMBiz {
    public List<RAMDto> selectAll();
    public RAMDto selectOne(int product_code);
    public int insert(RAMDto dto);

}
