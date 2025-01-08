package com.boot.selftop_web.quote.biz;

import java.util.List;

import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;
import com.boot.selftop_web.product.model.dto.ProductInfoDto;
import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.CartDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;
import com.boot.selftop_web.quote.model.dto.QuotecomparisonDto;

public interface QuoteBiz {

	List<QuoteDto> SelectQuote(int membernum);
	List<QuoteDetailDto> QuoteDetailinfo(int quote_no);


	List<CartDTO> selectCart(int member_no);
	List<CartDetailDto> selectCartDetail(int quote_no);

	ProductInfoDto selectProduct(int product_code);
	ProductStatusDto selectProductStatus(int product_code);

	List<QuotecomparisonDto> Quotecomprison(List<String> value);
	int quotedelete(List<Integer> value);
	int quotedetaildelete(List<Integer> value);
	int updatedetailamount(int quoteno,int productcode,int amount);
	
//	cpu ram 비교
	String cpuddr(int productcode);
	String ramddr(int productcode);
// cpu board 비교	
	String cpusocket(int productcode);
	String boardsocket(int productcode);
//	memroy board비교
	String boardmemoryslot(int productcode);
//	String ramddr
	
// case board 비교	
	String caseformfactor(int productcode);
	String boardformfactor(int productcode);
	
//	case gpu비교
	int vgalength(int productcode);
	int casevgalength(int productcode);
	
// case power 비교	
	String casepowersize(int productcode);
	String powersize(int productcode);
	
//	power watt 계산
	int wattvalue(int productcode,String category);
	
// 선택한 power watt 불러오기
	int powerwatt(int productcode);
	

}
