package com.boot.selftop_web.seller.model.mapper;

import java.util.List;

import com.boot.selftop_web.seller.model.dto.SellerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.boot.selftop_web.seller.model.dto.SellerOrderDto;

@Mapper
public interface SellerBoardMapper {
	
	@Select("SELECT * FROM sellermain WHERE customer_no= #{memberno} ")
	List<SellerOrderDto> selectList(@Param("memberno") int memberno);

	@Select("<script>" +
	        "SELECT * FROM sellermain " +
	        "WHERE 1=1 " +  
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
	List<SellerOrderDto> selectSearch(@Param("startdate") String startdate, @Param("enddate") String enddate, @Param("keyword")String keyword);


	@Select("SELECT * from customer where id = #{id}")
	SellerDto idchk(@Param("id") String id);

}
