package com.boot.selftop_web.member.seller.biz.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;

@Mapper
public interface ProductStatusMapper {
    @Insert("INSERT INTO PRODUCT_STATUS (PRODUCT_CODE, SELLER_NO, STOCK, PRICE, REG_DATE) " +
            "VALUES (#{product_code}, #{seller_no}, #{stock}, #{price}, #{reg_date})")
    int insertProductStatus(ProductStatusDto productStatus);

    @Delete("DELETE FROM PRODUCT_STATUS WHERE PRODUCT_CODE = #{product_code} AND SELLER_NO = #{seller_no}")
    int deleteProductStatus(@Param("product_code") int productCode, @Param("seller_no") int sellerNo);

}
