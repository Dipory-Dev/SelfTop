package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.PowerDto;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PowerMapper {
    @Insert("INSERT INTO POWER (PRODUCT_CODE, SUPPLY, PLUS80, FORMFACTOR) " +
            "VALUES (#{product_code}, #{supply}, #{plus80}, #{formfactor})")
    int insertPower(PowerDto dto);
    
}
