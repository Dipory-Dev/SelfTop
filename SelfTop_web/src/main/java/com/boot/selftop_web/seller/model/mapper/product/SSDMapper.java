package com.boot.selftop_web.seller.model.mapper.product;

import com.boot.selftop_web.seller.model.dto.product.SSDDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SSDMapper {
    @Insert("INSERT INTO SSD (PRODUCT_CODE, STORAGE, TYPE, WATT) " +
            "VALUES (#{product_code}, #{storage}, #{type}, #{watt})")
    int insertSSD(SSDDto dto);
}
