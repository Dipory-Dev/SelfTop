package com.boot.selftop_web.order.biz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {
    
	// OrderBoard 저장
    @Insert("INSERT INTO ORDER_BOARD VALUES (SEQ_ORDER.nextval, #{member_no}, CURRENT_DATE, #{address}, #{request}, '결제완료', NULL, NULL, #{assembly})")
    int insertOrderBoard(@Param("member_no") int member_no, @Param("address") String address, @Param("request") String request, @Param("assembly") String assembly);

    @Select("select SEQ_ORDER.currval from dual")
    int getCurrentOrderNo();

    // OrderDetail 저장
    @Insert("INSERT INTO ORDER_DETAIL VALUES (#{order_no}, #{member_no}, #{product_code}, #{seller_no}, #{amount}, #{order_price})")
    int insertOrderDetail(@Param("order_no") int order_no, @Param("member_no") int member_no, @Param("product_code") int product_code, @Param("seller_no") int seller_no, @Param("amount") int amount, @Param("order_price") int order_price);
    
    @Update("UPDATE product_status SET stock = stock - #{amount}  WHERE product_code =#{product_code} AND seller_no= #{seller_no}")
    int stockupdate(@Param("product_code")int product_code,@Param("seller_no")int seller_no,@Param("amount")int amount);
	
}
