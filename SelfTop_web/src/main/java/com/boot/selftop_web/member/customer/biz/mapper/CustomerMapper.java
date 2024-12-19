package com.boot.selftop_web.member.customer.biz.mapper;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CustomerMapper {
    @Insert("INSERT INTO customer VALUES (seq_member.nextval, #{id}, #{pw}, #{name}, #{email}, #{phone}, 'C')")
    int insertCustomer(CustomerDto dto);

    @Select("select seq_member.currval from dual")
    int getCurrentMemberNo();

    @Select("<script>" + "SELECT * FROM customer " + "WHERE ID = #{id} " + "AND PW = #{pw}" + "</script>")
    CustomerDto memberlogin(@Param("id") String id, @Param("pw") String pw);

    @Update("update customer set role = 'D' where email = #{email} and pw = #{pw}")
    int delUser(@Param("email") String eamil, @Param("pw") String pw);
}
