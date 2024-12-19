package com.boot.selftop_web.member.model.dto.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.selftop_web.member.model.dto.MemberDto;
import com.boot.selftop_web.seller.model.dto.SellerDto;

@Mapper
public interface MemberMapper {
	
	@Select("<script>" + "SELECT * FROM customer " + "WHERE ID = #{id} " + "AND PW = #{pw}" + "</script>")
	MemberDto memberlogin(@Param("id") String id, @Param("pw") String pw);

}
