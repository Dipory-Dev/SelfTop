package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.HDDDto;
import com.boot.selftop_web.product.biz.mapper.HDDMapper;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("HDD")
public class HDDBizImpl implements ProductBiz<HDDDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private HDDMapper hddMapper;

    @Override
    public List<HDDDto> selectAll() {
    	return null;
    }

    @Override
    public HDDDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(HDDDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += hddMapper.insertHDD(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public List<HDDDto> getProductsByCategory(String category, String sort, String searchTerm) {
    	return productMapper.findAllDetailedHddProducts(category, sort, searchTerm == null ? "" : searchTerm.trim());
    }

	@Override
	public List<HDDDto> filterProducts(Map<String, List<String>> filters, String sort) {
		try {
            return hddMapper.findFilteredHDDs(filters, sort);
        } catch (Exception e) {
            System.err.println("Error filtering HDDs with filters: " + filters + "\nError: " + e.getMessage());
            e.printStackTrace(); // 스택 추적을 통해 더 자세한 오류 정보 제공
            throw new RuntimeException("Error processing HDD filter", e);
        }
	}

}
