package com.boot.selftop_web.order.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.order.biz.mapper.OrderDetailMapper;
import com.boot.selftop_web.order.model.dto.OrderDetailDto;

@Service
public class OrderDetailBizImpl implements OrderDetailBiz {
	@Autowired
    private OrderDetailMapper mapper;
	
	@Override
    public List<OrderDetailDto> orderdetail(int ordernum) {
        return mapper.orderdetail(ordernum);
    }

    @Override
    public void saveOrderDetail(OrderDetailDto orderDetail) {
        mapper.insertOrderDetail(orderDetail);
    }

}
