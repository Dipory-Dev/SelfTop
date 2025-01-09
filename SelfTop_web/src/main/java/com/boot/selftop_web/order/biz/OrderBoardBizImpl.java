package com.boot.selftop_web.order.biz;

import java.util.List;
import java.util.Random;

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
	
	@Override
    public String getDeliveryNo(int order_no) {
        return mapper.getDeliveryNoByOrderNo(order_no);
    }
	
	public boolean completeOrder(int order_no) {
        return mapper.updateOrderStatusToComplete(order_no) > 0;
    }

	@Override
	public int requestcancelorder(int orderno, String reason) {
		// TODO Auto-generated method stub
		return mapper.requestcancelorder(orderno,reason);
	}

	

	@Override
	public int requestcancelorder(int orderno, String reason) {
		// TODO Auto-generated method stub
		return mapper.requestcancelorder(orderno,reason);
	}

}
