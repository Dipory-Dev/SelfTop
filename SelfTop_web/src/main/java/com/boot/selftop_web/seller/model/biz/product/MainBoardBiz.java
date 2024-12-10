package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.MainBoardDto;

import java.util.List;

public interface MainBoardBiz {
    public List<MainBoardDto> selectAll();
    public MainBoardDto selectOne(int product_code);
    public int insert(MainBoardDto dto);

}
