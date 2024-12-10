package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.GPUDto;

import java.util.List;

public interface GPUBiz {
    public List<GPUDto> selectAll();
    public GPUDto selectOne(int product_code);
    public int insert(GPUDto dto);

}
