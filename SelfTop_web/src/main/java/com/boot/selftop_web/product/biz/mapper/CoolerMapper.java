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
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = '쿨러'" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "JOIN COOLER c ON p.PRODUCT_CODE = c.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "WHERE p.CATEGORY = '쿨러' " +
            "<if test='search != null and search != \"\"'>" +
            " AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{search}) || '%' " +
            " OR LOWER(p.ETC) LIKE '%' || LOWER(#{search}) || '%')" +
            "</if>"+
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

            // 인기 순으로 정렬
            "       <when test='sort == \"bypopular\"'>" +
            "           COALESCE(weighted_rating, 0) DESC, p.PRODUCT_NAME" +
            "       </when>" +

            "       <otherwise>p.PRODUCT_NAME</otherwise>" +
            "   </choose>" +
            "</if>" +
    		"</script>")
    List<CoolerDto> findFilteredCoolers(@Param("filters") Map<String, List<String>> filters, @Param("sort") String sort, @Param("search") String search);
    
}
