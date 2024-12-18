package com.boot.selftop_web.member.model.dto.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.selftop_web.member.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	@Select("<script>" + "SELECT * FROM customer " + "WHERE ID = #{id} " + "AND PW = #{pw}" + "</script>")
	MemberDto memberlogin(@Param("id") String id, @Param("pw") String pw);

}
