package com.boot.selftop_web.member.seller.biz.mapper;

import java.util.List;

import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerStockDto;


@Mapper
public interface SellerProductMapper {
	
	@Select("SELECT * FROM viewsellerorder WHERE seller_no= #{member_no} ")
	List<SellerOrderDto> selectList(@Param("member_no") int member_no);
	
	@Select("SELECT * FROM sellerstock WHERE seller_no= #{member_no} ")
	List<SellerStockDto> selectstock(@Param("member_no") int member_no);

	

	@Select("<script>" +
	        "SELECT * FROM viewsellerorder " +
	        "WHERE customer_no= #{member_no} " +
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
			@Param("keyword")String keyword,@Param("member_no") int member_no);

//	스톡서치
	@Select("<script>" +
			"SELECT * FROM sellerstock " +
			"WHERE seller_no= #{member_no} " +
			 "<if test='keyword != null and !keyword.isEmpty()'>" +
		        " AND product_name LIKE '%' || #{keyword} || '%' " +
		        "</if>" +
		        "</script>")
	List<SellerStockDto> selectstocksearch(@Param("keyword")String keyword  ,@Param("member_no") int member_no);
	
//스톡업데이트
	@Update("UPDATE product_status SET price = #{price}, stock= #{amount} WHERE product_code = #{productcode} AND seller_no =#{member_no}")
	int updatestock(@Param("productcode") int productcode, @Param("price") int price, @Param("amount") int amount,@Param("member_no") int member_no);


	@Select("SELECT c.role, c.pw, c.email, s.MEMBER_NO, c.ID, s.CEO_NAME, c.NAME, s.COMPANY_NAME, c.PHONE, s.BUSINESS_LICENSE " +
            "FROM SELLER s " +
            "JOIN CUSTOMER c ON s.MEMBER_NO = c.MEMBER_NO " +
            "WHERE s.MEMBER_NO = #{member_no}")
    SellerDto getSellerInfoByMemberNo(@Param("member_no") int member_no);

}
