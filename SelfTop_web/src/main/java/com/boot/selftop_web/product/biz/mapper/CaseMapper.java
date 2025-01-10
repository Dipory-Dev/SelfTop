package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CaseDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CaseMapper {
    @Insert("INSERT INTO CASE_BOARD (PRODUCT_CODE, POWER_STATUS, FORMFACTOR, TOWER_SIZE, VGA_LENGTH,power_size) " +
            "VALUES (#{product_code}, #{power_status}, #{formfactor}, #{tower_size}, #{vga_length},#{power_size})")
    int insertCase_board(CaseDto dto);
    
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
            "        WHERE p_sub.CATEGORY = '케이스'" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "JOIN CASE_BOARD c ON p.PRODUCT_CODE = c.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "WHERE p.CATEGORY = '케이스' " +
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
            "<if test='filters.Power_Status != null'>" +
            "   AND c.POWER_STATUS IN " +
            "   <foreach item='powerStatus' collection='filters.Power_Status' open='(' separator=',' close=')'>" +
            "       #{powerStatus}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Formfactor != null'>" +
            "   AND c.FORMFACTOR LIKE '%' || " +
            "   <foreach item='formfactor' collection='filters.Formfactor' open='(' separator=',' close=')'>" +
            "       #{formfactor}" +
            "   </foreach> || '%'" +
            "</if>" +
            "<if test='filters.Tower_Size != null'>" +
            "   AND c.TOWER_SIZE IN " +
            "   <foreach item='towerSize' collection='filters.Tower_Size' open='(' separator=',' close=')'>" +
            "       #{towerSize}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.VGA_Length != null'>" +
            "   AND c.VGA_LENGTH IN " +
            "   <foreach item='vgaLength' collection='filters.VGA_Length' open='(' separator=',' close=')'>" +
            "       #{vgaLength}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Power_Size != null'>" +
            "   AND c.POWER_SIZE LIKE '%' || " +
            "   <foreach item='powerSize' collection='filters.Power_Size' open='(' separator=',' close=')'>" +
            "       #{powerSize}" +
            "   </foreach> || '%'" +
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
    List<CaseDto> findFilteredCases(@Param("filters") Map<String, List<String>> filters, @Param("sort") String sort, @Param("search") String search);
    
}
