package com.boot.selftop_web.seller.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.seller.model.dto.SellerDto;
import com.boot.selftop_web.seller.model.mapper.SellerBoardMapper;

@Service
public class SellerBizImpl implements SellerBiz {
	
	@Autowired
	private SellerBoardMapper mapper;

	@Override
	public List<SellerDto> selectList(int memberno) {
		return mapper.selectList(memberno);
	}

	@Override
	public List<SellerDto> selectSearch(String startdate, String enddate,String keyword) {
		// TODO Auto-generated method stub
		return mapper.selectSearch(startdate, enddate,keyword);
	}





}
