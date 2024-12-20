package com.boot.selftop_web.member.customer.biz;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.customer.biz.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberBizImpl implements MemberBiz {

	@Autowired
	public CustomerMapper mapper;
	
	@Override
	public CustomerDto memberlogin(String id, String pw) {
		
		return mapper.memberlogin(id, pw);
	}

	
}
