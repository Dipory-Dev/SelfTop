package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CPUDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CPUMapper {
    @Insert("INSERT INTO CPU (PRODUCT_CODE, SOCKET, DDR, GENERATION, SPEC, INNER_VGA, PACKAGE_TYPE, COOLER_STATUS, CORE, WATT) " +
            "VALUES (#{product_code}, #{socket}, #{ddr}, #{generation}, #{spec}, #{inner_vga}, #{package_type}, #{cooler_status}, #{core}, #{watt})")
    int insertCPU(CPUDto dto);
    
    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN CPU c ON p.PRODUCT_CODE = c.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = 'CPU' " +
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
            "<if test='filters.Socket != null'>" +
            "   AND c.SOCKET IN " +
            "   <foreach item='socket' collection='filters.Socket' open='(' separator=',' close=')'>" +
            "       #{socket}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.DDR != null'>" +
            "   AND c.DDR IN " +
            "   <foreach item='ddr' collection='filters.DDR' open='(' separator=',' close=')'>" +
            "       #{ddr}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Generation != null'>" +
            "   AND c.GENERATION IN " +
            "   <foreach item='generation' collection='filters.Generation' open='(' separator=',' close=')'>" +
            "       #{generation}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Spec != null'>" +
            "   AND c.SPEC IN " +
            "   <foreach item='spec' collection='filters.Spec' open='(' separator=',' close=')'>" +
            "       #{spec}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Inner_VGA != null'>" +
            "   AND c.INNER_VGA IN " +
            "   <foreach item='innerVga' collection='filters.Inner_VGA' open='(' separator=',' close=')'>" +
            "       #{innerVga}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Package_Type != null'>" +
            "   AND c.PACKAGE_TYPE IN " +
            "   <foreach item='packageType' collection='filters.Package_Type' open='(' separator=',' close=')'>" +
            "       #{packageType}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Cooler_Status != null'>" +
            "   AND c.COOLER_STATUS IN " +
            "   <foreach item='coolerStatus' collection='filters.Cooler_Status' open='(' separator=',' close=')'>" +
            "       #{coolerStatus}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Core != null'>" +
            "   AND c.CORE IN " +
            "   <foreach item='core' collection='filters.Core' open='(' separator=',' close=')'>" +
            "       #{core}" +
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
    List<CPUDto> findFilteredCPUs(@Param("filters") Map<String, List<String>> filters, @Param("sort") String sort, @Param("search") String search);

}
