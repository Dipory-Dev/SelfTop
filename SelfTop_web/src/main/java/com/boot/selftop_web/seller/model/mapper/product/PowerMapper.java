package com.boot.selftop_web.seller.model.mapper.product;

import com.boot.selftop_web.seller.model.dto.product.PowerDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PowerMapper {
    @Insert("INSERT INTO POWER (PRODUCT_CODE, SUPPLY, PLUS80, FORMFACTOR) " +
            "VALUES (#{product_code}, #{supply}, #{plus80}, #{formfactor})")
    int insertPower(PowerDto dto);
}
