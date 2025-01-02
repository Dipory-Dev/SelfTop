package com.boot.selftop_web.quote.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.selftop_web.quote.biz.mapper.QuoteMapper;
import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;

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

}
