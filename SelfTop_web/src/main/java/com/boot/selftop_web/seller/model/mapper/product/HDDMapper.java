package com.boot.selftop_web.seller.model.mapper.product;

import com.boot.selftop_web.seller.model.dto.product.HDDDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HDDMapper {
    @Insert("INSERT INTO HDD (PRODUCT_CODE, DEVICE, STORAGE, WATT) " +
            "VALUES (#{product_code}, #{device}, #{storage}, #{watt})")
    int insertHDD(HDDDto dto);
}
