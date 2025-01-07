package com.boot.selftop_web.order.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.order.biz.mapper.OrderBoardMapper;
import com.boot.selftop_web.order.model.dto.OrderBoardDto;
import com.boot.selftop_web.order.model.dto.OrderDetailDto;
import com.boot.selftop_web.product.model.dto.ProductDto;

@Service
public class OrderBoardBizImpl implements OrderBoardBiz{
	@Autowired
	private OrderBoardMapper mapper;
	
	@Override
	public List<OrderBoardDto> vieworderboard(int ordernum) {		
		return mapper.vieworderboard(ordernum);
	}

	@Override
	public List<OrderBoardDto> selectOrderBoard(int member_no) {
		return mapper.selectOrderBoard(member_no);
	}

	@Override
	public List<OrderDetailDto> selectOrderDetail(int order_no) {
		return mapper.selectOrderDetail(order_no);
	}

	@Override
	public ProductDto selectProduct(int product_code) {
		return null;
	}

}
