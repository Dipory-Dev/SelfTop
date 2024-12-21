package com.boot.selftop_web.order.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.order.biz.mapper.OrderBoardMapper;
import com.boot.selftop_web.order.model.dto.OrderBoardDto;

@Service
public class OrderBoardBizImpl implements OrderBoardBiz{
	@Autowired
	private OrderBoardMapper mapper;
	
	@Override
	public List<OrderBoardDto> vieworderboard(int ordernum) {
		
		return mapper.vieworderboard(ordernum);
	}

}
