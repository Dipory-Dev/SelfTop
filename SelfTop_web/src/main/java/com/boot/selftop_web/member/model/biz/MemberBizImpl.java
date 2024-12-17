package com.boot.selftop_web.member.model.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.member.model.dto.MemberDto;
import com.boot.selftop_web.member.model.dto.mapper.MemberMapper;

@Service
public class MemberBizImpl implements MemberBiz {

	@Autowired
	public MemberMapper mapper;
	
	@Override
	public MemberDto memberlogin(String id, String pw) {
		
		return mapper.memberlogin(id, pw);
	}

	
}
