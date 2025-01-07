package com.boot.selftop_web.order.biz;

import java.util.List;
import java.util.Random;

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
	
	@Override
    public boolean updateOrder(int order_no) {
        String currentStatus = mapper.checkOrderStatus(order_no);
        if ("배송중".equals(currentStatus)) {
            return false;
        }

        String delivery_no = generateRandomDeliveryNo(10);
        mapper.updateOrderStatus(order_no, delivery_no);
        return true;
    }

    private String generateRandomDeliveryNo(int length) {
        Random random = new Random();
        return random.ints(48, 58)
                     .limit(length)
                     .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                     .toString();
    }

}
