package com.boot.selftop_web.member.customer.biz;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
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
	public String checkpw(CustomerDto dto) {
		return mapper.checkpw(dto);
	}
	
	@Override
	public int changepw(CustomerDto dto, String pw) {
		return mapper.changepw(dto, pw);
	}

	@Override
	public int delUser(String id, String email, String pw) {
		return mapper.delUser(id, email, pw);
	}

	@Override
	public boolean idchk(String id) {
		SellerDto res = mapper.idchk(id);
		System.out.println("biz : " + res);
		if (res == null){
			return true;
		} else {
			return false;
		}
	}

	// 구매자 회원가입
	@Override
	public int insertCustomer(CustomerDto dto) {
		return mapper.insertCustomer(dto);
	}

}
