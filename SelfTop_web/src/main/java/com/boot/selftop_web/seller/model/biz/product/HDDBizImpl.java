package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.HDDDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HDDBizImpl implements HDDBiz{
    @Override
    public List<HDDDto> selectAll() {
        return null;
    }

    @Override
    public HDDDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(HDDDto dto) {
        return 0;
    }
}
