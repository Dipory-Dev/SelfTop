package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.HDDDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HDDMapper {
    @Insert("INSERT INTO HDD (PRODUCT_CODE, DEVICE, STORAGE, WATT) " +
            "VALUES (#{product_code}, #{device}, #{storage}, #{watt})")
    int insertHDD(HDDDto dto);
}
