package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.SSDDto;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import com.boot.selftop_web.product.biz.mapper.SSDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	public List<SSDDto> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}


}
