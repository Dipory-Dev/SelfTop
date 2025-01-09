package com.boot.selftop_web.order.biz;

import java.util.List;

import com.boot.selftop_web.order.model.dto.OrderBoardDto;
import com.boot.selftop_web.order.model.dto.OrderDetailDto;
import com.boot.selftop_web.product.model.dto.ProductDto;

public interface OrderBoardBiz {
	public List<OrderBoardDto> vieworderboard(int ordernum);
	
	List<OrderBoardDto> selectOrderBoard(int member_no);
	List<OrderDetailDto> selectOrderDetail(int order_no);
	
	ProductDto selectProduct(int product_code);
	boolean updateOrder(int order_no); // 주문 상태 업데이트 (배송중 상태로)
    boolean completeOrder(int order_no); // 주문 상태를 배송완료로 업데이트

	String getDeliveryNo(int order_no); // 주문 번호에 해당하는 송장 번호 가져오기
	int requestcancelorder(int orderno,String reason);
}
