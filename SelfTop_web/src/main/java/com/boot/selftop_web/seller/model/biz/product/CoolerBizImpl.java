package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.CoolerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoolerBizImpl implements CoolerBiz{
    @Override
    public List<CoolerDto> selectAll() {
        return null;
    }

    @Override
    public CoolerDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(CoolerDto dto) {
        return 0;
    }
}
