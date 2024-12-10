package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.CoolerDto;

import java.util.List;

public interface CoolerBiz {
    public List<CoolerDto> selectAll();
    public CoolerDto selectOne(int product_code);
    public int insert(CoolerDto dto);

}
