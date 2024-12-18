package com.boot.selftop_web.seller.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.seller.model.dto.SellerDto;
import com.boot.selftop_web.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.seller.model.dto.SellerStockDto;
import com.boot.selftop_web.seller.model.mapper.SellerBoardMapper;

@Service
public class SellerBizImpl implements SellerBiz {
	
	@Autowired
	private SellerBoardMapper mapper;

	@Override
	public List<SellerOrderDto> selectList(int memberno) {
		return mapper.selectList(memberno);
	}

	@Override
	public List<SellerOrderDto> selectSearch(String startdate, String enddate, String keyword,int memberno) {
		// TODO Auto-generated method stub
		return mapper.selectSearch(startdate, enddate,keyword,memberno);
	}

	@Override
	public boolean idchk(String id) {
		System.out.println("biz : " + id);
		SellerOrderDto res = mapper.idchk(id);
		System.out.println("biz :" + res);
		if (res == null){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<SellerStockDto> selectStock(int memberno) {

		return mapper.selectstock(memberno);
	}

	public List<SellerStockDto> selectStocksearch(String keyword, int memberno) {
		// TODO Auto-generated method stub
		return mapper.selectstocksearch(keyword, memberno);
	}





}
