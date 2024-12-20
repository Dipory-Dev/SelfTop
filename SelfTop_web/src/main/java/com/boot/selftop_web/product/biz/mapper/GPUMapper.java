package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.GPUDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GPUMapper {
    @Insert("INSERT INTO GPU (PRODUCT_CODE, SERIES, STORAGE, LENGTH, WATT) " +
            "VALUES (#{product_code}, #{series}, #{storage}, #{length}, #{watt})")
    int insertGPU(GPUDto dto);
}
