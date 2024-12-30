package com.boot.selftop_web.member.customer.biz;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.customer.model.dto.CustomerorderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.member.customer.biz.mapper.CustomerMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

	@Override
	public CustomerDto selectCustomer(int member_no) {
		CustomerDto res = mapper.selectCustomer(member_no);
		return res;
	}

	@Override
	public int changeInfo(String email, String phone, int member_no)
	{
		return mapper.changeInfo(email, phone, member_no);
	}

	@Override
	public CustomerDto findkakao(Map<String, Object> userInfo) {
		// 카카오 사용자 정보를 받아서 DTO로 변환 후 반환
		String id = (String) userInfo.get("id");

		return mapper.findkakao(id);
		// 최초 로그인 시 null 반환으로 kakaoinsert 메서드 실행되도록 구성
	}

	@Override
	public int kakaoinsert(CustomerDto dto) {
		// 카카오 사용자 정보를 받아서 고객 등록
		System.out.println("userinfo 비즈 " + dto);
		return mapper.insertCustomer(dto); // 카카오 사용자 저장
	}

	@Override
	public List<SellerOrderDto> selectcustomerorderlist(int member_no) {

		return mapper.selectcustomerorderlist(member_no);
	}

	@Override
	public List<SellerOrderDto> searchcustomerorderlist(String startdate, String enddate, int member_no) {
		// TODO Auto-generated method stub
		return mapper.searchcustomerorderlist(startdate,enddate,member_no);
	}
}
