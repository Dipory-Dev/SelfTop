package com.boot.selftop_web.quote.biz;

import java.util.List;

import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;

public interface QuoteBiz {

	List<QuoteDto> SelectQuote(int membernum);
	List<QuoteDetailDto> QuoteDetailinfo(int quote_no);
}
