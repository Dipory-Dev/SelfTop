package com.boot.selftop_web.seller.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.boot.selftop_web.seller.model.dto.SellerDto;

@Mapper
public interface SellerBoardMapper {
	
	@Select(" SELECT * FROM ORDER_BOARD")
	List<SellerDto> selectList();

	@Select("<script>" +
	        "SELECT * FROM ORDER_BOARD " +
	        "WHERE 1=1 " +  
	        "<if test='startdate != null and !startdate.isEmpty()'>" +
	        "   AND order_date &gt;= #{startdate} " +
	        "</if>" +
	        "<if test='enddate != null and !enddate.isEmpty()'>" +
	        "   AND order_date &lt;= #{enddate} " +
	        "</if>" +
	        "<if test='keyword != null and !keyword.isEmpty()'>" +
	        " AND p_model LIKE '%' || #{keyword} || '%' " +	
	        "</if>" +
	        "</script>")
	List<SellerDto> selectSearch(@Param("startdate") String startdate, @Param("enddate") String enddate,@Param("keyword")String keyword);

}
