package com.boot.selftop_web.member.model.biz;


import com.boot.selftop_web.member.model.dto.MemberDto;


public interface MemberBiz {
	public MemberDto memberlogin(String id, String pw);

}
