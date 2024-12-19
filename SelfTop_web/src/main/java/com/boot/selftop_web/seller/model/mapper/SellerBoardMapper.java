package com.boot.selftop_web.seller.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.boot.selftop_web.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.seller.model.dto.SellerStockDto;
import com.boot.selftop_web.seller.model.dto.SellerDto;

@Mapper
public interface SellerBoardMapper {
	
	@Select("SELECT * FROM sellermain WHERE customer_no= #{memberno} ")
	List<SellerOrderDto> selectList(@Param("memberno") int memberno);

	@Select("SELECT * FROM sellerstock WHERE seller_no= #{memberno} ")
	List<SellerStockDto> selectstock(@Param("memberno") int memberno);

	@Select("<script>" +
	        "SELECT * FROM sellermain " +
	        "WHERE customer_no= #{memberno} " +
	        "<if test='startdate != null and !startdate.isEmpty()'>" +
	        "   AND order_date &gt;= #{startdate} " +
	        "</if>" +
	        "<if test='enddate != null and !enddate.isEmpty()'>" +
	        "   AND order_date &lt;= #{enddate} " +
	        "</if>" +
	        "<if test='keyword != null and !keyword.isEmpty()'>" +
	        " AND product_name LIKE '%' || #{keyword} || '%' " +	
	        "</if>" +
	        "</script>")
	List<SellerOrderDto> selectSearch(@Param("startdate") String startdate, @Param("enddate") String enddate,
			@Param("keyword")String keyword,@Param("memberno") int memberno);


//	스톡서치
	@Select("<script>" +
			"SELECT * FROM sellerstock " +
			"WHERE seller_no= #{memberno} " +
			 "<if test='keyword != null and !keyword.isEmpty()'>" +
		        " AND product_name LIKE '%' || #{keyword} || '%' " +
		        "</if>" +
		        "</script>")
	List<SellerStockDto> selectstocksearch(@Param("keyword")String keyword  ,@Param("memberno") int memberno);


	@Select("SELECT * from customer where id = #{id}")
	SellerDto idchk(@Param("id") String id);

	@Select("SELECT s.MEMBER_NO, c.ID, s.CEO_NAME, c.NAME, s.COMPANY_NAME, c.PHONE, s.BUSINESS_LICENSE " +
            "FROM SELLER s " +
            "JOIN CUSTOMER c ON s.MEMBER_NO = c.MEMBER_NO " +
            "WHERE s.MEMBER_NO = #{member_no}")
    SellerDto getSellerInfoByMemberNo(@Param("member_no") int member_no);
	
}
