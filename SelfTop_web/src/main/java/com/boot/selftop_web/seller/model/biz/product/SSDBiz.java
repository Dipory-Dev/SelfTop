package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.SSDDto;

import java.util.List;

public interface SSDBiz {
    public List<SSDDto> selectAll();
    public SSDDto selectOne(int product_code);
    public int insert(SSDDto dto);

}
