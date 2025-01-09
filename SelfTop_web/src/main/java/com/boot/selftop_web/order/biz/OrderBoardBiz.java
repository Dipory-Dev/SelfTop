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
	boolean updateOrder(int order_no);
	int requestcancelorder(int orderno,String reason);
}
