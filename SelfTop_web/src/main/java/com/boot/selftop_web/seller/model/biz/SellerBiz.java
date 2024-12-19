package com.boot.selftop_web.seller.model.biz;

import java.util.List;

import com.boot.selftop_web.seller.model.dto.SellerDto;
import com.boot.selftop_web.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.seller.model.dto.SellerStockDto;

public interface SellerBiz {
//	판매자한테 들어온 주문 정보
	public List<SellerOrderDto> selectList(int memberno);
	public List<SellerOrderDto> selectSearch(String startdate, String enddate, String keyword, int memberno);

	// ID 중복체크
	public boolean idchk(String id);
	// 판매자 회원가입
	public int insertSeller(SellerDto dto);

	public List<SellerStockDto> selectStock(int memberno);
	public SellerDto getSellerInfoByMemberNo(int member_no);


}