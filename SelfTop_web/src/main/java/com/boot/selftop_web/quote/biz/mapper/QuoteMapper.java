package com.boot.selftop_web.quote.biz.mapper;

import java.util.List;

import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.CartDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;

@Mapper
public interface QuoteMapper {

	@Select("SELECT "
	        + "q.QUOTE_NO, "
	        + "q.Customer_no, "
	        + "q.quote_name, "
	        + "q.quote_date, "
	        + "q.quote_amount, "
	        + "SUM(ps.price * qd.amount) AS price "
	        + "FROM "
	        + "QUOTE q "
	        + "JOIN QUOTE_DETAIL qd ON q.QUOTE_NO = qd.QUOTE_NO "
	        + "JOIN PRODUCT_STATUS ps ON qd.PRODUCT_CODE = ps.PRODUCT_CODE AND ps.SELLER_NO = qd.SELLER_NO "
	        + "WHERE q.customer_no = #{membernum} "
	        + "GROUP BY q.QUOTE_NO, q.CUSTOMER_NO, q.QUOTE_NAME, q.QUOTE_DATE, q.QUOTE_AMOUNT")
	List<QuoteDto> SelectQuote(@Param("membernum") int membernum);
	
	@Select("SELECT qd.*,p.CATEGORY,p.THUMBNAIL ,p.PRODUCT_NAME,ps.price\r\n"
			+ " FROM QUOTE_DETAIL qd\r\n"
			+ " JOIN PRODUCT p ON qd.PRODUCT_CODE = p.PRODUCT_CODE \r\n"
			+ " JOIN PRODUCT_STATUS ps ON ps.PRODUCT_CODE =qd.PRODUCT_CODE "
			+ " WHERE quote_no=#{quote_no}")
	List<QuoteDetailDto> QuoteDetailinfo(@Param("quote_no")int quote_no);

	@Select("SELECT * FROM QUOTE q WHERE q.CUSTOMER_NO = #{member_no} ORDER BY q.QUOTE_NO desc")
	List<CartDTO> selectCart(@Param("member_no") int member_no);

	@Select("select * from quote_detail where quote_no = #{quote_no}")
	List<CartDetailDto> selectCartDetail(@Param("quote_no")int quote_no);
}
