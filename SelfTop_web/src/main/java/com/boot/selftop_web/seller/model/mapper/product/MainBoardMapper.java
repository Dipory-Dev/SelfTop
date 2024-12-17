package com.boot.selftop_web.seller.model.mapper.product;

import com.boot.selftop_web.seller.model.dto.product.MainBoardDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MainBoardMapper {
    @Insert("INSERT INTO MAINBOARD (PRODUCT_CODE, SOCKET, FORMFACTOR, MEMORY_SLOT, DDR, MAX_STORAGE, WATT) " +
            "VALUES (#{product_code}, #{socket}, #{formfactor}, #{memory_slot}, #{ddr}, #{max_storage}, #{watt})")
    int insertMainBoard(MainBoardDto dto);
}
