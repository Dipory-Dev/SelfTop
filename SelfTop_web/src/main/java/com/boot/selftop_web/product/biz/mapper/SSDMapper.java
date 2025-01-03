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
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN SSD s ON p.PRODUCT_CODE = s.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = 'SSD' " +
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
            "</script>")
    List<SSDDto> findFilteredSSDs(@Param("filters") Map<String, List<String>> filters);
}
