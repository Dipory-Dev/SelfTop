package com.boot.selftop_web.quote.biz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CartMapper {
	 // Cart 저장
    @Insert("INSERT INTO QUOTE VALUES (SEQ_QUOTE.nextval, #{member_no}, #{quoteName}, CURRENT_DATE, 1, #{assembly})")
    int insertCart(@Param("member_no") int member_no, @Param("quoteName") String quoteName, @Param("assembly") String assembly);

    @Select("select SEQ_QUOTE.currval from dual")
    int getCurrentQuoteNo();

    // CartItem 저장
    @Insert("INSERT INTO QUOTE_DETAIL VALUES (#{quote_no}, #{member_no}, #{product_code}, #{seller_no}, #{amount})")
    int insertCartDetail(@Param("quote_no") int quote_no, @Param("member_no") int member_no, @Param("product_code") int product_code, @Param("seller_no") int seller_no, @Param("amount") int amount);
	
    // ProductCode 조회
	@Select("SELECT product_code FROM products WHERE PRODUCT_NAME = #{name}")
	String findProductCodeByName(@Param("name") String name);

}
