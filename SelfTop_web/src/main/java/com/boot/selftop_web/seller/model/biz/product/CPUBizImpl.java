package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.CPUDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CPUBizImpl implements CPUBiz{
    @Override
    public List<CPUDto> selectAll() {
        return null;
    }

    @Override
    public CPUDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(CPUDto cpu) {
        return 0;
    }
}
