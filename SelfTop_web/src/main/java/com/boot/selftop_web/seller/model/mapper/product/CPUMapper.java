package com.boot.selftop_web.seller.model.mapper.product;

import com.boot.selftop_web.seller.model.dto.product.CPUDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CPUMapper {
    @Insert("INSERT INTO CPU (PRODUCT_CODE, SOCKET, DDR, GENERATION, SPEC, INNER_VGA, PACKAGE_TYPE, COOLER_STATUS, CORE, WATT) " +
            "VALUES (#{product_code}, #{socket}, #{ddr}, #{generation}, #{spec}, #{inner_vga}, #{package_type}, #{cooler_status}, #{core}, #{watt})")
    int insertCPU(CPUDto dto);
}
