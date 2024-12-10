package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.SSDDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SSDBizImpl implements SSDBiz{
    @Override
    public List<SSDDto> selectAll() {
        return null;
    }

    @Override
    public SSDDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(SSDDto dto) {
        return 0;
    }
}
