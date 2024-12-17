package com.boot.selftop_web.seller.model.mapper.product;

import com.boot.selftop_web.seller.model.dto.product.RAMDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RAMMapper {
    @Insert("INSERT INTO RAM (PRODUCT_CODE, DDR, STORAGE, DEVICE, HEATSYNC, WATT) " +
            "VALUES (#{product_code}, #{ddr}, #{storage}, #{device}, #{heatsync}, #{watt})")
    int insertRAM(RAMDto dto);
}
