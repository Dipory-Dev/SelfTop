package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.MainBoardDto;
import com.boot.selftop_web.product.biz.mapper.MainBoardMapper;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("메인보드")
public class MainBoardBizImpl implements ProductBiz<MainBoardDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private MainBoardMapper mainBoardMapper;

    @Override
    public List<MainBoardDto> selectAll() {
    	return null;
    }

    @Override
    public MainBoardDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(MainBoardDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += mainBoardMapper.insertMainBoard(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public List<MainBoardDto> getProductsByCategory(String category, String sort, String searchTerm) {
    	return productMapper.findAllDetailedMainBoardProducts(category, sort, searchTerm == null ? "" : searchTerm.trim());
    }

	@Override
	public List<MainBoardDto> filterProducts(Map<String, List<String>> filters, String sort) {
		try {
            return mainBoardMapper.findFilteredMainBoards(filters, sort);
        } catch (Exception e) {
            System.err.println("Error filtering MainBoards with filters: " + filters + "\nError: " + e.getMessage());
            e.printStackTrace(); // 스택 추적을 통해 더 자세한 오류 정보 제공
            throw new RuntimeException("Error processing MainBoard filter", e);
        }
	}

}
