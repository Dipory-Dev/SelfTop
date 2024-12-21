package com.boot.selftop_web.member.seller.biz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;

@Mapper
public interface ProductStatusMapper {
    @Insert("INSERT INTO PRODUCT_STATUS (PRODUCT_CODE, SELLER_NO, STOCK, PRICE, REG_DATE) " +
            "VALUES (#{product_code}, #{seller_no}, #{stock}, #{price}, #{reg_date})")
    int insertProductStatus(ProductStatusDto productStatus);
}
