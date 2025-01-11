package com.boot.selftop_web.member.customer.biz;


import java.util.List;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.customer.model.dto.CustomerorderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;

import java.util.HashMap;
import java.util.Map;


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

	public CustomerDto findkakao(Map<String, Object> userInfo);

	public int kakaoinsert(CustomerDto dto);

	public List<SellerOrderDto> selectcustomerorderlist(int member_no);

	public List<SellerOrderDto> searchcustomerorderlist(String startdate,String enddate,int member_no);
	
	public  List<SellerOrderDto> customerpurchaselist(int member_no,int order_no);

	public  int insertReview(String filePath, String content, int rating, int product_code, int member_no);
	
	public int updateReviewimage(String filePath, String content, int rating, int product_code, int member_no);
	
}
