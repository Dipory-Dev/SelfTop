package com.boot.selftop_web.product.biz;

import com.boot.selftop_web.product.model.dto.CaseDto;
import com.boot.selftop_web.product.biz.mapper.CaseMapper;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("케이스")
public class CaseBizImpl implements ProductBiz<CaseDto> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CaseMapper caseMapper;
    @Override
    public List<CaseDto> selectAll() {
        return null;
    }

    @Override
    public CaseDto selectOne(int product_code) {
        return null;
    }

    @Override
    @Transactional
    public int insert(CaseDto dto) {
        System.out.println("Biz : " + dto);
        int result = 0;
        try {
            result = productMapper.insertProduct(dto);
            System.out.println("Biz : " + result);
            int productCode = productMapper.getCurrentProductCode();
            dto.setProduct_code(productCode);
            result += caseMapper.insertCase_board(dto);
            System.out.println("Biz : " + result);
        } catch (Exception e) {
            System.err.println("Error during insert: " + e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public List<CaseDto> getProductsByCategory(String category, String sort) {
        List<CaseDto> results = productMapper.findAllDetailedCaseProducts(category, sort);

        return results;
    }

	@Override
	public List<CaseDto> filterProducts(Map<String, List<String>> filters, String sort) {
		try {
            return caseMapper.findFilteredCases(filters, sort);
        } catch (Exception e) {
            System.err.println("Error filtering Cases with filters: " + filters + "\nError: " + e.getMessage());
            e.printStackTrace(); // 스택 추적을 통해 더 자세한 오류 정보 제공
            throw new RuntimeException("Error processing CASE filter", e);
        }
	}

}
