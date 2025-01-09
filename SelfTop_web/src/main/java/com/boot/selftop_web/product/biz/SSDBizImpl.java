package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.SSDDto;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import com.boot.selftop_web.product.biz.mapper.SSDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("SSD")
public class SSDBizImpl implements ProductBiz<SSDDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SSDMapper ssdMapper;

    @Override
    public List<SSDDto> selectAll() {
    	return null;
    }

    @Override
    public SSDDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(SSDDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += ssdMapper.insertSSD(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public List<SSDDto> getProductsByCategory(String category, String sort, String searchTerm) {
    	return productMapper.findAllDetailedSsdProducts(category, sort, searchTerm == null ? "" : searchTerm.trim());
    }

	@Override
	public List<SSDDto> filterProducts(Map<String, List<String>> filters, String sort, String search) {
		try {
            return ssdMapper.findFilteredSSDs(filters, sort, search);
        } catch (Exception e) {
            System.err.println("Error filtering SSDs with filters: " + filters + "\nError: " + e.getMessage());
            e.printStackTrace(); // 스택 추적을 통해 더 자세한 오류 정보 제공
            throw new RuntimeException("Error processing SSD filter", e);
        }
	}

}
