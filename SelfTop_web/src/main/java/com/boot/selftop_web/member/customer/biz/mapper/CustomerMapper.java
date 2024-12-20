package com.boot.selftop_web.member.customer.biz.mapper;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import org.apache.ibatis.annotations.*;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerMapper {
    @Insert("INSERT INTO customer VALUES (seq_member.nextval, #{id}, #{pw}, #{name}, #{email}, #{phone}, 'C')")
    int insertCustomer(CustomerDto dto);

    @Select("select seq_member.currval from dual")
    int getCurrentMemberNo();

    @Select("<script>" + "SELECT * FROM customer " + "WHERE ID = #{id} " + "AND PW = #{pw}" + "</script>")
    CustomerDto memberlogin(@Param("id") String id, @Param("pw") String pw);

    @Select("SELECT pw FROM customer WHERE member_no = #{dto.member_no}")
    String checkpw(@Param("dto") CustomerDto dto);

    @Update("UPDATE customer SET pw = #{new_pw} WHERE member_no = #{dto.member_no}")
    int changepw(@Param("dto") CustomerDto dto, @Param("new_pw") String new_pw);

    @Update("update customer set role = 'D' where id = #{id} and email = #{email} and pw = #{pw}")
    int delUser(@Param("id") String id, @Param("email") String eamil, @Param("pw") String pw);

}
