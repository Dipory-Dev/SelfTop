package com.boot.selftop_web.quote.biz;

import java.util.List;
import java.util.Map;

import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;
import com.boot.selftop_web.product.model.dto.ProductInfoDto;
import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.CartDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.quote.biz.mapper.QuoteMapper;
import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;
import com.boot.selftop_web.quote.model.dto.QuotecomparisonDto;

@Service
public class QuoteBizImpl implements QuoteBiz{

	@Autowired
	private QuoteMapper mapper;
	@Override
	public List<QuoteDto> SelectQuote(int membernum) {
		// TODO Auto-generated method stub
		return mapper.SelectQuote(membernum);
	}
	@Override
	public List<QuoteDetailDto> QuoteDetailinfo(int quote_no) {
		// TODO Auto-generated method stub
		return mapper.QuoteDetailinfo(quote_no);
	}
	@Override
	public List<QuotecomparisonDto> Quotecomprison(List<String> value) {
		// TODO Auto-generated method stub
		return mapper.Quotecomprison(value);
	}
	@Override
	public int quotedelete(List<Integer> value) {
		// TODO Auto-generated method stub
		return mapper.quotedelete(value);
	}
	@Override
	public int quotedetaildelete(List<Integer> value) {
		// TODO Auto-generated method stub
		return mapper.quotedetaildelete(value);
	}
	@Override
	public int updatedetailamount(int quoteno, int productcode, int amount) {
		// TODO Auto-generated method stub
		return mapper.updatedetailamount(quoteno,productcode,amount);
	}

	@Override
	public List<CartDTO> selectCart(int member_no) {
		return mapper.selectCart(member_no);
	}

	@Override
	public List<CartDetailDto> selectCartDetail(int quote_no) {
		return mapper.selectCartDetail(quote_no);
	}

	@Override
	public ProductInfoDto selectProduct(int product_code) {
		return mapper.selectProduct(product_code);
	}


	@Override
	public ProductStatusDto selectProductStatus(int product_code) {
		return mapper.selectProductStatus(product_code);
	}


	@Override
	public String cpuddr(int productcode) {
		// TODO Auto-generated method stub
		return mapper.cpuddr(productcode);
	}
	@Override
	public String ramddr(int productcode) {
		// TODO Auto-generated method stub
		return mapper.ramddr(productcode);
	}
	@Override
	public String cpusocket(int productcode) {
		// TODO Auto-generated method stub
		return mapper.cpusocket(productcode);
	}
	@Override
	public String boardsocket(int productcode) {
		// TODO Auto-generated method stub
		return mapper.boardsocket(productcode);
	}
	@Override
	public String boardmemoryslot(int productcode) {
		// TODO Auto-generated method stub
		return mapper.boardmemoryslot(productcode);
	}
	@Override
	public String caseformfactor(int productcode) {
		// TODO Auto-generated method stub
		return mapper.caseformfactor(productcode);
	}
	@Override
	public String boardformfactor(int productcode) {
		// TODO Auto-generated method stub
		return mapper.boardformfactor(productcode);
	}
	@Override
	public int vgalength(int productcode) {
		// TODO Auto-generated method stub
		return mapper.vgalength(productcode);
	}
	@Override
	public int casevgalength(int productcode) {
		// TODO Auto-generated method stub
		return mapper.casevgalength(productcode);
	}
	@Override
	public String casepowersize(int productcode) {
		// TODO Auto-generated method stub
		return mapper.casepowersize(productcode);
	}
	@Override
	public String powersize(int productcode) {
		// TODO Auto-generated method stub
		return mapper.powersize(productcode);
	}
	@Override
	public int wattvalue(int productcode, String category) {
		// TODO Auto-generated method stub
		return mapper.wattvalue(productcode,category);
	}
	@Override
	public int powerwatt(int productcode) {
		// TODO Auto-generated method stub
		return mapper.powerwatt(productcode);
	}
	@Override
	public char assemblecheck(int quoteno) {
		// TODO Auto-generated method stub
		return mapper.assemblecheck(quoteno);
	}

}
