package com.boot.selftop_web.order.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.selftop_web.order.model.dto.OrderDetailDto;

@Mapper
public interface OrderDetailMapper {
	// 주문 상세 정보를 저장하는 메서드
    @Insert("INSERT INTO ORDER_DETAIL (order_no, customer_no, product_code, seller_no, amount, order_price) " +
            "VALUES (#{order_no}, #{customer_no}, #{product_code}, #{seller_no}, #{amount}, #{order_price})")
    void insertOrderDetail(OrderDetailDto orderDetail);
	
    // 특정 주문번호에 해당하는 주문 상세 정보를 조회하는 메서드
    @Select("SELECT * FROM ORDER_DETAIL WHERE order_no = #{ordernum}")
    public List<OrderDetailDto> orderdetail(@Param("ordernum") int ordernum);

}
