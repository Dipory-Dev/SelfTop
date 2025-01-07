package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CoolerDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CoolerMapper {
    @Insert("INSERT INTO COOLER (PRODUCT_CODE, COOLER_TYPE, SOCKET, WATT) " +
            "VALUES (#{product_code}, #{cooler_type}, #{socket}, #{watt})")
    int insertCooler(CoolerDto dto);
    
    @Select("<script>" + 
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN COOLER c ON p.PRODUCT_CODE = c.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = '쿨러' " +
            "<if test='filters.Company != null'>" +
            "   AND p.COMPANY IN " +
            "   <foreach item='company' collection='filters.Company' open='(' separator=',' close=')'>" +
            "       #{company}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Cooler_Type != null'>" +
            "   AND c.COOLER_TYPE IN " +
            "   <foreach item='coolerType' collection='filters.Cooler_Type' open='(' separator=',' close=')'>" +
            "       #{coolerType}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Socket != null'>" +
            "   AND c.SOCKET IN " +
            "   <foreach item='socket' collection='filters.Socket' open='(' separator=',' close=')'>" +
            "       #{socket}" +
            "   </foreach>" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC" +
            "<if test='sort != null'>" +  // 정렬 조건 추가
            "ORDER BY " +
            "   <choose>" +
            "       <when test='sort == \"bynewest\"'>MAX(p.upload_date) DESC</when>" +
            "       <when test='sort == \"bylowprice\"'>" +
            "           CASE WHEN MIN(ps.PRICE) IS NULL THEN 1 ELSE 0 END, price" +
            "       </when>" +
            "       <when test='sort == \"byhighprice\"'>" +
            "           CASE WHEN MIN(ps.PRICE) IS NULL THEN 1 ELSE 0 END, price DESC" +
            "       </when>" +
            "       <otherwise>p.PRODUCT_NAME</otherwise>" +
            "   </choose>" +
            "</if>" +
    		"</script>")
    List<CoolerDto> findFilteredCoolers(@Param("filters") Map<String, List<String>> filters, @Param("sort") String sort);
    
}
