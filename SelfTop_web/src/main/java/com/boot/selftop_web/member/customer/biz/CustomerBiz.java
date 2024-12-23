package com.boot.selftop_web.member.customer.biz;


import com.boot.selftop_web.member.customer.model.dto.CustomerDto;


public interface CustomerBiz {
	public CustomerDto memberlogin(String id, String pw);
	public int delUser(String id, String email, String pw);

	//기존 비밀번호 가져오기
	public String checkpw(CustomerDto dto);

	//비밀번호 변경
	public int changepw(CustomerDto dto, String pw);

	public boolean idchk(String id);

	public int insertCustomer(CustomerDto dto);

	public CustomerDto selectCustomer(int member_no);

	public int changeInfo(String email, String phone, int member_no);
}
