package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.SSDDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SSDMapper {
    @Insert("INSERT INTO SSD (PRODUCT_CODE, STORAGE, TYPE, WATT) " +
            "VALUES (#{product_code}, #{storage}, #{type}, #{watt})")
    int insertSSD(SSDDto dto);
    
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
            "        WHERE p_sub.CATEGORY = 'SSD'" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "JOIN SSD s ON p.PRODUCT_CODE = s.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "WHERE p.CATEGORY = 'SSD' " +
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
            "<if test='filters.Storage != null'>" +
            "   AND s.STORAGE IN " +
            "   <foreach item='storage' collection='filters.Storage' open='(' separator=',' close=')'>" +
            "       #{storage}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Type != null'>" +
            "   AND s.TYPE IN " +
            "   <foreach item='type' collection='filters.Type' open='(' separator=',' close=')'>" +
            "       #{type}" +
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
    List<SSDDto> findFilteredSSDs(@Param("filters") Map<String, List<String>> filters, @Param("sort") String sort, @Param("search") String search);
}
