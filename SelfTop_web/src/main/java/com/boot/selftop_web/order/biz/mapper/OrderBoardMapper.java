package com.boot.selftop_web.order.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.boot.selftop_web.order.model.dto.OrderBoardDto;
import com.boot.selftop_web.order.model.dto.OrderDetailDto;
import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.CartDetailDto;
import com.boot.selftop_web.review.model.dto.ReviewDto;
import com.boot.selftop_web.review.model.dto.reviewsearchDto;

@Mapper
public interface OrderBoardMapper {
	@Select("SELECT * from vieworderboard WHERE order_no =#{ordernum} ")
	public List<OrderBoardDto> vieworderboard(@Param("ordernum") int ordernum);
	
	@Select("SELECT * FROM ORDER_BOARD WHERE CUSTOMER_NO = #{member_no} ORDER BY ORDER_NO desc")
	List<OrderBoardDto> selectOrderBoard(@Param("member_no") int member_no);

    @Select("select * from ORDER_DETAIL where ORDER_NO = #{order_no}")
    List<OrderDetailDto> selectOrderDetail(@Param("order_no")int quote_no);

    @Update("UPDATE ORDER_BOARD SET delivery_no = #{delivery_no}, order_status = '배송중' WHERE order_no = #{order_no} AND order_status != '배송중'")
    int updateOrderStatus(@Param("order_no") int order_no, @Param("delivery_no") String delivery_no);

    @Select("SELECT order_status FROM ORDER_BOARD WHERE order_no = #{order_no}")
    String checkOrderStatus(@Param("order_no") int order_no);

    @Select("SELECT delivery_no FROM order_board WHERE order_no = #{order_no}")
    String getDeliveryNoByOrderNo(@Param("order_no") int order_no);

    @Update("UPDATE ORDER_BOARD SET order_status = '배송완료' WHERE order_no = #{order_no}")
    int updateOrderStatusToComplete(@Param("order_no") int order_no);

    @Update("UPDATE order_board SET order_status = '취소요청', reason = #{reason} WHERE order_no = #{orderno} ")
	public int requestcancelorder(@Param("orderno")int orderno,@Param("reason") String reason);

    @Select("Select * from review where member_no = #{memberno}")
	public List<reviewsearchDto> reviewsearchres(@Param("memberno") int memberno);

    @Select("Select review_no from where member_no =#{memberno}")
	public int searchreviewnum(int memberno);
}
