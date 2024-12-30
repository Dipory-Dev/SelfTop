package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CoolerDto;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoolerMapper {
    @Insert("INSERT INTO COOLER (PRODUCT_CODE, COOLER_TYPE, SOCKET, WATT) " +
            "VALUES (#{product_code}, #{cooler_type}, #{socket}, #{watt})")
    int insertCooler(CoolerDto dto);
    
}
