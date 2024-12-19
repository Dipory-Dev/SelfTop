package com.boot.selftop_web.seller.model.biz;

import java.util.List;

import com.boot.selftop_web.seller.model.mapper.CustomerMapper;
import com.boot.selftop_web.seller.model.mapper.SellerMapper;
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

	@Autowired
	private SellerMapper sellerMapper;
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public List<SellerOrderDto> selectList(int memberno) {
		return mapper.selectList(memberno);
	}

	@Override
	public List<SellerOrderDto> selectSearch(String startdate, String enddate, String keyword, int memberno) {
		// TODO Auto-generated method stub
		return mapper.selectSearch(startdate, enddate,keyword,memberno);
	}

	@Override
	public boolean idchk(String id) {
		System.out.println("biz : " + id);
		SellerDto res = mapper.idchk(id);
		System.out.println("biz :" + res);
		if (res == null){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int insertSeller(SellerDto dto) {
		System.out.println("biz : " + dto);
		int res = 0;
		res += customerMapper.insertCustomer(dto);
		System.out.println("biz : " + res);
		dto.setMember_no(customerMapper.getCurrentMemberNo());
		res += sellerMapper.insertSeller(dto);
		System.out.println("biz : " + res);
		res += sellerMapper.updateRole(dto);
		System.out.println("biz : " + res);
		return res;
	}
	
	@Override
    public SellerDto getSellerInfoByMemberNo(int member_no) {
        return mapper.getSellerInfoByMemberNo(member_no);
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
