package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.RAMDto;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import com.boot.selftop_web.product.biz.mapper.RAMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("RAM")
public class RAMBizImpl implements ProductBiz<RAMDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RAMMapper ramMapper;

    @Override
    public List<RAMDto> selectAll() {
    	return null;
    }

    @Override
    public RAMDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(RAMDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += ramMapper.insertRAM(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }

	@Override
	public List<RAMDto> getProductsByCategory(String category) {
		if ("ram".equalsIgnoreCase(category)) {
            return productMapper.findAllDetailedRamProducts();
        }
        return new ArrayList<>();
	}


}
