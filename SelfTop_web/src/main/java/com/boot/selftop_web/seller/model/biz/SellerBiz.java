package com.boot.selftop_web.seller.model.biz;

import java.util.List;

import com.boot.selftop_web.seller.model.dto.SellerOrderDto;

public interface SellerBiz {
<<<<<<< HEAD
//	판매자한테 들어온 주문 정보
	public List<SellerOrderDto> selectList(int memeberno);
	public List<SellerOrderDto> selectSearch(String startdate, String enddate, String keyword);
=======
	public List<SellerDto> selectList(int memberno);
	public List<SellerDto> selectSearch(String startdate,String enddate,String keyword);

>>>>>>> d879f07 ([MainPage, sellerMyPage 수정])

	public boolean idchk(String id);

}
