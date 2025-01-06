package com.boot.selftop_web.order.biz;

import java.util.List;

import com.boot.selftop_web.order.model.dto.OrderDetailDto;

public interface OrderDetailBiz {
	// 주문 상세 정보 조회
    List<OrderDetailDto> orderdetail(int ordernum);

    // 주문 상세 정보 저장
    void saveOrderDetail(OrderDetailDto orderDetail);
}
