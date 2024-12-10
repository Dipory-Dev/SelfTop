package com.boot.selftop_web.seller.model.biz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boot.selftop_web.seller.model.dto.SellerDto;

@Service
public interface SellerBiz {
	public List<SellerDto> selectList();
	public List<SellerDto> selectSearch(String startdate,String enddate,String keyword);



}
