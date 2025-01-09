package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.GPUDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GPUMapper {
    @Insert("INSERT INTO GPU (PRODUCT_CODE, SERIES, STORAGE, LENGTH, WATT) " +
            "VALUES (#{product_code}, #{series}, #{storage}, #{length}, #{watt})")
    int insertGPU(GPUDto dto);
    
    @Select("<script>" + 
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN GPU g ON p.PRODUCT_CODE = g.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = '그래픽카드' " +
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
            "<if test='filters.Series != null'>" +
            "   AND g.SERIES IN " +
            "   <foreach item='series' collection='filters.Series' open='(' separator=',' close=')'>" +
            "       #{series}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Storage != null'>" +
            "   AND g.STORAGE IN " +
            "   <foreach item='storage' collection='filters.Storage' open='(' separator=',' close=')'>" +
            "       #{storage}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Length != null'>" +
            "   AND g.LENGTH IN " +
            "   <foreach item='length' collection='filters.Length' open='(' separator=',' close=')'>" +
            "       #{length}" +
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
    List<GPUDto> findFilteredGPUs(@Param("filters") Map<String, List<String>> filters, @Param("sort") String sort, @Param("search") String search);
    
}
