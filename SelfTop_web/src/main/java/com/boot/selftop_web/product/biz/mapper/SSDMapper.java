package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.SSDDto;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SSDMapper {
    @Insert("INSERT INTO SSD (PRODUCT_CODE, STORAGE, TYPE, WATT) " +
            "VALUES (#{product_code}, #{storage}, #{type}, #{watt})")
    int insertSSD(SSDDto dto);
    
}
