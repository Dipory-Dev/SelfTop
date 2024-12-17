package com.boot.selftop_web.seller.model.mapper.product;

import com.boot.selftop_web.seller.model.dto.product.CoolerDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CoolerMapper {
    @Insert("INSERT INTO COOLER (PRODUCT_CODE, COOLER_TYPE, SOCKET, WATT) " +
            "VALUES (#{product_code}, #{cooler_type}, #{socket}, #{watt})")
    int insertCooler(CoolerDto dto);
}
