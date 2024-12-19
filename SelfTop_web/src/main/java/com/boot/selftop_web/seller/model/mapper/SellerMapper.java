package com.boot.selftop_web.seller.model.mapper;

import com.boot.selftop_web.seller.model.dto.SellerDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SellerMapper {
    @Insert("INSERT INTO seller VALUES (#{member_no}, #{company_name}, #{ceo_name}, #{business_license}, #{address})")
    int insertSeller(SellerDto dto);

    @Update("update customer set role = 'S' where member_no = #{member_no}")
    int updateRole(SellerDto dto);
}
