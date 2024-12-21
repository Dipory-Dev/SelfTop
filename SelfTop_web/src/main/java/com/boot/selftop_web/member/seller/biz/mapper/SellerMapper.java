package com.boot.selftop_web.member.seller.biz.mapper;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

@Mapper
public interface SellerMapper {
    @Insert("INSERT INTO seller VALUES (#{member_no}, #{company_name}, #{ceo_name}, #{business_license}, #{address})")
    int insertSeller(SellerDto dto);

    @Update("update customer set role = 'S' where member_no = #{member_no}")
    int updateRole(SellerDto dto);
    
    @Update("UPDATE customer SET phone = #{phone} WHERE member_no = #{dto.member_no}")
    int updatePhone(@Param("dto") SellerDto dto, @Param("phone") String phone);
    
    @Update("UPDATE seller SET address = #{address} WHERE member_no = #{dto.member_no}")
    int updateAddress(@Param("dto") SellerDto dto, @Param("address") String address);

}
