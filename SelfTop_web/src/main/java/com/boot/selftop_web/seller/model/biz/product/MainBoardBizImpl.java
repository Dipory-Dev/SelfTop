package com.boot.selftop_web.seller.model.biz.product;

import com.boot.selftop_web.seller.model.dto.product.MainBoardDto;
import com.boot.selftop_web.seller.model.mapper.product.MainBoardMapper;
import com.boot.selftop_web.seller.model.mapper.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
