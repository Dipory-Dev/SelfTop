package com.boot.selftop_web.member.seller.biz;

import java.util.List;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerStockDto;

public interface SellerBiz {
//	판매자한테 들어온 주문 정보

	public List<SellerOrderDto> selectList(int memeberno);
	public List<SellerOrderDto> selectSearch(String startdate, String enddate, String keyword,int memberno);
//	재고 확인
	public List<SellerStockDto> selectStock(int memberno);
	public List<SellerStockDto> selectStocksearch(String keywrod,int memberno);
	public int updatestock(int productcode,int price,int amount,int membernum);


	// ID 중복체크
	public boolean idchk(String id);
	// 판매자 회원가입
	public int insertSeller(CustomerDto customerDto, SellerDto dto);
	public SellerDto getSellerInfoByMemberNo(int member_no);
	

}