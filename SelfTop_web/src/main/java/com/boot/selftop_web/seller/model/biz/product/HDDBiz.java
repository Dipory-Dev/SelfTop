package com.boot.selftop_web.seller.model.biz.product.biz_backup;

import com.boot.selftop_web.seller.model.dto.product.HDDDto;

import java.util.List;

public interface HDDBiz {
    public List<HDDDto> selectAll();
    public HDDDto selectOne(int product_code);
    public int insert(HDDDto dto);

}
