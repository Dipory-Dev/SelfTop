package com.boot.selftop_web.member.customer.biz;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.member.customer.biz.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerBizImpl implements CustomerBiz {

	@Autowired
	public CustomerMapper mapper;
	
	@Override
	public CustomerDto memberlogin(String id, String pw) {
		
		return mapper.memberlogin(id, pw);
	}

	@Override
	public int delUser(String email, String pw) {
		return mapper.delUser(email, pw);
	}

	@Override
	public boolean verifyPW(String pw) {
		CustomerDto res = mapper.verifyPW(pw);
		System.out.println("biz :" + res);
		if (res == null){
			return true;
		} else {
			return false;
		}
	}
}
