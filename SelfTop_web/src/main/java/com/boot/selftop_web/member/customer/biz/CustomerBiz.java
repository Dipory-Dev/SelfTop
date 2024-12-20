package com.boot.selftop_web.member.customer.biz;


import com.boot.selftop_web.member.customer.model.dto.CustomerDto;


public interface CustomerBiz {
	public CustomerDto memberlogin(String id, String pw);
	public int delUser(String email, String pw);
	
	//기존 비밀번호 확인
	public boolean verifyPW(String pw);
}
