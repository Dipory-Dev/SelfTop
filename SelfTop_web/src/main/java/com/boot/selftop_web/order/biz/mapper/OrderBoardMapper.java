package com.boot.selftop_web.order.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.boot.selftop_web.order.model.dto.OrderBoardDto;

@Mapper
public interface OrderBoardMapper {
	@Select("SELECT * from vieworderboard WHERE order_no =#{ordernum} ")
	public List<OrderBoardDto> vieworderboard(@Param("ordernum") int ordernum);
	

	@Update("UPDATE ORDER_BOARD SET delivery_no = #{delivery_no}, order_status = '배송중' WHERE order_no = #{order_no} AND order_status != '배송중'")
    int updateOrderStatus(@Param("order_no") int order_no, @Param("delivery_no") String delivery_no);

    @Select("SELECT order_status FROM ORDER_BOARD WHERE order_no = #{order_no}")
    String checkOrderStatus(@Param("order_no") int order_no);
}
