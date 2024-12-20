package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CaseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaseMapper {
    @Insert("INSERT INTO CASE_BOARD (PRODUCT_CODE, POWER_STATUS, FORMFACTOR, TOWER_SIZE, VGA_LENGTH) " +
            "VALUES (#{product_code}, #{power_status}, #{formfactor}, #{tower_size}, #{vga_length})")
    int insertCase_board(CaseDto dto);
}
