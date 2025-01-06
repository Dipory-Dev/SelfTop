package com.boot.selftop_web.quote.biz;

import java.util.List;

import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;

public interface QuoteBiz {

	List<QuoteDto> SelectQuote(int membernum);
	List<QuoteDetailDto> QuoteDetailinfo(int quote_no);

	List<CartDTO> selectCart(int member_no);
}
