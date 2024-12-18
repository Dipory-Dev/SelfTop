package com.boot.selftop_web.seller.model.biz;

import java.util.List;

import com.boot.selftop_web.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.seller.model.dto.SellerStockDto;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.seller.model.dto.SellerDto;

public interface SellerBiz {
//	판매자한테 들어온 주문 정보
	public List<SellerOrderDto> selectList(int memberno);
	public List<SellerOrderDto> selectSearch(String startdate, String enddate, String keyword,int memberno);
//	재고 확인
	public List<SellerStockDto> selectStock(int memberno);
	public List<SellerStockDto> selectStocksearch(String keywrod,int memberno);

	public boolean idchk(String id);

}
