package com.boot.selftop_web.quote.biz;

import org.springframework.stereotype.Service;

import com.boot.selftop_web.quote.biz.mapper.CartMapper;
import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.CartItemDTO;

@Service
public class CartBizImpl {
	
	private final CartMapper cartMapper;

    public CartBizImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    public void saveCart(CartDTO cartDTO) {
    	
        // Cart 저장
        cartMapper.insertCart(cartDTO.getQuoteName());

        // CartItem 처리
        for (CartItemDTO item : cartDTO.getItems()) {
            // name을 기준으로 productCode 조회
            String productCode = cartMapper.findProductCodeByName(item.getName());

            if (productCode == null) {
                throw new IllegalArgumentException("Product not found for name: " + item.getName());
            }

            // productCode를 cartItemDTO에 추가
            item.setProductCode(productCode);

            // CartItem 저장
            cartMapper.insertCartItem(item);
        }
    }

}
