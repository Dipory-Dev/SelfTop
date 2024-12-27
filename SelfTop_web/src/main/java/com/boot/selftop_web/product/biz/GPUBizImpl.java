package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.GPUDto;
import com.boot.selftop_web.product.biz.mapper.GPUMapper;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("그래픽카드")
public class GPUBizImpl implements ProductBiz<GPUDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private GPUMapper gpuMapper;

    @Override
    public List<GPUDto> selectAll() {
        return null;
    }

    @Override
    public GPUDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(GPUDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += gpuMapper.insertGPU(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }

	@Override
	public List<GPUDto> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

}
