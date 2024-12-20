package com.boot.selftop_web.member.admin.biz.mapper;

import com.boot.selftop_web.member.admin.model.dto.AdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("<script>" + "SELECT * FROM ADMIN " + "WHERE ID = #{id} " + "AND PASSWORD = #{password}" + "</script>")
    AdminDto selectAdmin(@Param("id") String id, @Param("password") String password);
}
