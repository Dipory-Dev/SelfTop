package com.boot.selftop_web.seller.model.mapper;

import com.boot.selftop_web.seller.model.dto.SellerDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerMapper {
    @Insert("INSERT INTO customer VALUES (seq_member.nextval, #{id}, #{pw}, #{name}, #{email}, #{phone}, 'C')")
    int insertCustomer(SellerDto dto);

    @Select("select seq_member.currval from dual")
    int getCurrentMemberNo();
}
