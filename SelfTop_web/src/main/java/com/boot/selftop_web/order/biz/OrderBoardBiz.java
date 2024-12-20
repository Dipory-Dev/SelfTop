package com.boot.selftop_web.order.biz;

import java.util.List;

import com.boot.selftop_web.order.model.dto.OrderBoardDto;

public interface OrderBoardBiz {
	public List<OrderBoardDto> vieworderboard(int ordernum);

}
