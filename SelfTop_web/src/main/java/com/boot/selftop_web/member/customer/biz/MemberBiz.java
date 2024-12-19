package com.boot.selftop_web.member.customer.biz;


import com.boot.selftop_web.member.customer.model.dto.CustomerDto;


public interface MemberBiz {
	public CustomerDto memberlogin(String id, String pw);

}
