package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.GPUDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GPUBizImpl implements GPUBiz{
    @Override
    public List<GPUDto> selectAll() {
        return null;
    }

    @Override
    public GPUDto selectOne(int product_code) {
        return null;
    }

    @Override
    public int insert(GPUDto dto) {
        return 0;
    }
}
