package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CPUDto;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CPUMapper {
    @Insert("INSERT INTO CPU (PRODUCT_CODE, SOCKET, DDR, GENERATION, SPEC, INNER_VGA, PACKAGE_TYPE, COOLER_STATUS, CORE, WATT) " +
            "VALUES (#{product_code}, #{socket}, #{ddr}, #{generation}, #{spec}, #{inner_vga}, #{package_type}, #{cooler_status}, #{core}, #{watt})")
    int insertCPU(CPUDto dto);
    
}
