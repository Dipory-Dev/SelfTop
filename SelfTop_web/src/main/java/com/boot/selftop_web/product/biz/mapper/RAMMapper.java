package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.RAMDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RAMMapper {
    @Insert("INSERT INTO RAM (PRODUCT_CODE, DDR, STORAGE, DEVICE, HEATSYNC, WATT) " +
            "VALUES (#{product_code}, #{ddr}, #{storage}, #{device}, #{heatsync}, #{watt})")
    int insertRAM(RAMDto dto);
}
