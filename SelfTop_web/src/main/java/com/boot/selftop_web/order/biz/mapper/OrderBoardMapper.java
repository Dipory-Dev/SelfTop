package com.boot.selftop_web.order.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.selftop_web.order.model.dto.OrderBoardDto;

@Mapper
public interface OrderBoardMapper {
	@Select("SELECT * from vieworderboard WHERE order_no =#{ordernum} ")
	public List<OrderBoardDto> vieworderboard(@Param("ordernum") int ordernum);

}
