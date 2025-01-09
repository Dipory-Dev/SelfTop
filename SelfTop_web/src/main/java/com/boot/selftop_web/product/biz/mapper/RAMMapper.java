package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.RAMDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RAMMapper {
    @Insert("INSERT INTO RAM (PRODUCT_CODE, DDR, STORAGE, DEVICE, HEATSYNC, WATT) " +
            "VALUES (#{product_code}, #{ddr}, #{storage}, #{device}, #{heatsync}, #{watt})")
    int insertRAM(RAMDto dto);
    
    @Select("<script>" + 
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN RAM r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = 'RAM' " +
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
            "<if test='filters.DDR != null'>" +
            "   AND r.DDR IN " +
            "   <foreach item='ddr' collection='filters.DDR' open='(' separator=',' close=')'>" +
            "       #{ddr}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Storage != null'>" +
            "   AND r.STORAGE IN " +
            "   <foreach item='storage' collection='filters.Storage' open='(' separator=',' close=')'>" +
            "       #{storage}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Device != null'>" +
            "   AND r.DEVICE IN " +
            "   <foreach item='device' collection='filters.Device' open='(' separator=',' close=')'>" +
            "       #{device}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Heatsync != null'>" +
            "   AND r.HEATSYNC IN " +
            "   <foreach item='heatsync' collection='filters.Heatsync' open='(' separator=',' close=')'>" +
            "       #{heatsync}" +
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
    List<RAMDto> findFilteredRAMs(@Param("filters") Map<String, List<String>> filters, @Param("sort") String sort, @Param("search") String search);
}
