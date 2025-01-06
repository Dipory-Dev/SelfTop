package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.CoolerDto;
import com.boot.selftop_web.product.biz.mapper.CoolerMapper;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("쿨러")
public class CoolerBizImpl implements ProductBiz<CoolerDto>{

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CoolerMapper coolerMapper;

    @Override
    public List<CoolerDto> selectAll() {
        return null;
    }

    @Override
    public CoolerDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(CoolerDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += coolerMapper.insertCooler(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public List<CoolerDto> getProductsByCategory(String category, String sort) {
        List<CoolerDto> results = productMapper.findAllDetailedCoolerProducts(category, sort);

        return results;
    }

	@Override
	public List<CoolerDto> filterProducts(Map<String, List<String>> filters, String sort) {
		try {
            return coolerMapper.findFilteredCoolers(filters, sort);
        } catch (Exception e) {
            System.err.println("Error filtering Coolers with filters: " + filters + "\nError: " + e.getMessage());
            e.printStackTrace(); // 스택 추적을 통해 더 자세한 오류 정보 제공
            throw new RuntimeException("Error processing COOLER filter", e);
        }
	}

}
