package com.boot.selftop_web.seller.model.biz.product.biz_backup;

import com.boot.selftop_web.seller.model.dto.product.PowerDto;

import java.util.List;

public interface PowerBiz {
    public List<PowerDto> selectAll();
    public PowerDto selectOne(int product_code);
    public int insert(PowerDto dto);

}
