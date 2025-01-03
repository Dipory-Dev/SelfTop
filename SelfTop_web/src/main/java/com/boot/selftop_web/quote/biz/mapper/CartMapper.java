package com.boot.selftop_web.quote.biz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.selftop_web.quote.model.dto.CartItemDTO;

@Mapper
public interface CartMapper {
	 // Cart 저장
    void insertCart(@Param("quoteName") String quoteName);

    // CartItem 저장
    void insertCartItem(CartItemDTO cartItem);
	
    // ProductCode 조회
	@Select("SELECT product_code FROM products WHERE PRODUCT_NAME = #{name}")
	String findProductCodeByName(@Param("name") String name);

}
