package com.boot.selftop_web.quote.biz;

import java.util.List;

import com.boot.selftop_web.product.model.dto.ProductDto;
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

	ProductDto selectProduct(int product_code);

	List<QuotecomparisonDto> Quotecomprison(List<String> value);
	int quotedelete(List<Integer> value);
	int quotedetaildelete(List<Integer> value);
	int updatedetailamount(int quoteno,int productcode,int amount);

}
