package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.RAMDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RAMBizImpl implements RAMBiz{
    @Override
    public List<RAMDto> selectAll() {
        return null;
    }

    @Override
    public RAMDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(RAMDto dto) {
        return 0;
    }
}
