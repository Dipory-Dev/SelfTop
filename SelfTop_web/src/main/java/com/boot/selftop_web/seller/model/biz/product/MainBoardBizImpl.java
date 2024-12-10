package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.MainBoardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainBoardBizImpl implements MainBoardBiz{
    @Override
    public List<MainBoardDto> selectAll() {
        return null;
    }

    @Override
    public MainBoardDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(MainBoardDto dto) {
        return 0;
    }
}
