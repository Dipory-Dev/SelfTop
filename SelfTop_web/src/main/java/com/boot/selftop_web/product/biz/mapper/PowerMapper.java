package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.PowerDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PowerMapper {
    @Insert("INSERT INTO POWER (PRODUCT_CODE, SUPPLY, PLUS80, FORMFACTOR) " +
            "VALUES (#{product_code}, #{supply}, #{plus80}, #{formfactor})")
    int insertPower(PowerDto dto);
    
    @Select("<script>" + 
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN POWER c ON p.PRODUCT_CODE = c.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = '파워' " +
            "<if test='filters.Company != null'>" +
            "   AND p.COMPANY IN " +
            "   <foreach item='company' collection='filters.Company' open='(' separator=',' close=')'>" +
            "       #{company}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Supply != null'>" +
            "   AND c.SUPPLY IN " +
            "   <foreach item='supply' collection='filters.Supply' open='(' separator=',' close=')'>" +
            "       #{supply}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Plus80 != null'>" +
            "   AND c.PLUS80 IN " +
            "   <foreach item='plus80' collection='filters.Plus80' open='(' separator=',' close=')'>" +
            "       #{plus80}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Formfactor != null'>" +
            "   AND c.FORMFACTOR IN " +
            "   <foreach item='formfactor' collection='filters.Formfactor' open='(' separator=',' close=')'>" +
            "       #{formfactor}" +
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
    List<PowerDto> findFilteredPowers(@Param("filters") Map<String, List<String>> filters, @Param("sort") String sort);
}
