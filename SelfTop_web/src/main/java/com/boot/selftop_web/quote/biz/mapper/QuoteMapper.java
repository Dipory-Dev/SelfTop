package com.boot.selftop_web.quote.biz.mapper;

import java.util.List;


import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;
import com.boot.selftop_web.product.model.dto.ProductInfoDto;
import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.CartDetailDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;
import com.boot.selftop_web.quote.model.dto.QuotecomparisonDto;

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

	@Select("select product_code, amount from quote_detail where quote_no = #{quote_no}")
	List<CartDetailDto> selectCartDetail(@Param("quote_no")int quote_no);

	@Select("select product_name, category from product where product_code = #{product_code}")
	ProductInfoDto selectProduct(@Param("product_code")int product_code);

	@Select("select seller_no, price from product_status where product_code = #{product_code} " +
			"and price = (select min(price) from product_status where product_code = #{product_code})" +
			"and reg_date = (select min(reg_date) from product_status where product_code = #{product_code})")
	ProductStatusDto selectProductStatus(@Param("product_code")int product_code);

	@Select("<script>" +
			"SELECT qd.quote_no,q.quote_name,qd.amount,p.CATEGORY,p.PRODUCT_NAME,ps.price * qd.amount AS price \r\n"
	+ " FROM QUOTE_DETAIL qd\r\n"
	+ " JOIN PRODUCT p ON qd.PRODUCT_CODE = p.PRODUCT_CODE \r\n"
	+ " JOIN PRODUCT_STATUS ps ON ps.PRODUCT_CODE =qd.PRODUCT_CODE AND qd.SELLER_NO=ps.SELLER_NO "
	+ " JOIN QUOTE q ON q.QUOTE_NO = qd.QUOTE_NO "
	+ " WHERE qd.quote_no IN "
	+ "<foreach item='quoteNo' collection='value' open='(' close=')' separator=','>"
	+  "#{quoteNo}" 
	+  "</foreach>" 
	+  "</script>")
	List<QuotecomparisonDto> Quotecomprison(@Param("value")List<String> value);
	
	@Delete("<script>" +
	        "DELETE FROM quote " +
	        "WHERE quote_no IN "+
	        "<foreach item='quoteNo' collection='value' open='(' separator=',' close=')'>" +
	        "#{quoteNo} " +
	        "</foreach>" +
	        "</script>")
	int quotedelete(@Param("value")List<Integer> value);
	
	@Delete("<script>" +
	        "DELETE FROM quote_detail " +
	        "WHERE quote_no IN "+
	        "<foreach item='quoteNo' collection='value' open='(' separator=',' close=')'>" +
	        "#{quoteNo} " +
	        "</foreach>" +
	        "</script>")
	int quotedetaildelete(@Param("value")List<Integer> value);

	@Update("UPDATE quote_detail SET amount = #{amount} WHERE quote_no=#{quoteno} AND product_code=#{productcode}")
	int updatedetailamount(@Param("quoteno")int quoteno,@Param("productcode") int productcode,@Param("amount") int amount);
	
//cpu ram 비교	
	@Select("SELECT ddr from cpu where product_code=#{productcode} ")
	String cpuddr(@Param("productcode") int productcode);
	@Select("SELECT ddr from ram where product_code=#{productcode} ")
	String ramddr(@Param("productcode") int productcode);
// cpu board 비교	
	@Select("SELECT socket from cpu where product_code=#{productcode} ")
	String cpusocket(@Param("productcode") int productcode);
	@Select("SELECT socket from mainboard where product_code=#{productcode} ")
	String boardsocket(@Param("productcode") int productcode);
//	memroy board비교
	@Select("SELECT ddr from mainboard where product_code=#{productcode} ")
	String boardmemoryslot(@Param("productcode") int productcode);

	
// case board 비교	
	@Select("SELECT formfactor from case_board where product_code=#{productcode} ")
	String caseformfactor(@Param("productcode") int productcode);
	@Select("SELECT formfactor from mainboard where product_code=#{productcode} ")
	String boardformfactor(@Param("productcode") int productcode);
	
//	case gpu비교
	@Select("SELECT length from gpu where product_code=#{productcode} ")
	int vgalength(@Param("productcode") int productcode);
	@Select("SELECT VGA_length from case_board where product_code=#{productcode} ")
	int casevgalength(@Param("productcode") int productcode);
	
// case power 비교	
	@Select("SELECT power_size from case_board where product_code=#{productcode} ")
	String casepowersize(@Param("productcode") int productcode);
	@Select("SELECT formfactor from power where product_code=#{productcode} ")
	String powersize(@Param("productcode") int productcode);

	@Select("SELECT watt from ${category} where product_code = #{productcode}")
	int wattvalue(@Param("productcode")int productcode,@Param("category") String category);

	@Select("Select supply from power where product_code =  #{productcode} ")
	int powerwatt(@Param("productcode")int productcode);

}
