package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.PowerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerBizImpl implements PowerBiz{
    @Override
    public List<PowerDto> selectAll() {
        return null;
    }

    @Override
    public PowerDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(PowerDto dto) {
        return 0;
    }
}
