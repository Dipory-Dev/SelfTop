package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.HDDDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HDDMapper {
    @Insert("INSERT INTO HDD (PRODUCT_CODE, DEVICE, STORAGE, WATT) " +
            "VALUES (#{product_code}, #{device}, #{storage}, #{watt})")
    int insertHDD(HDDDto dto);
    
    @Select("<script>" + 
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN HDD h ON p.PRODUCT_CODE = h.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = 'HDD' " +
            "<if test='filters.Company != null'>" +
            "   AND p.COMPANY IN " +
            "   <foreach item='company' collection='filters.Company' open='(' separator=',' close=')'>" +
            "       #{company}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Device != null'>" +
            "   AND h.DEVICE IN " +
            "   <foreach item='device' collection='filters.Device' open='(' separator=',' close=')'>" +
            "       #{device}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Storage != null'>" +
            "   AND h.STORAGE IN " +
            "   <foreach item='storage' collection='filters.Storage' open='(' separator=',' close=')'>" +
            "       #{storage}" +
            "   </foreach>" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC" +
            "</script>")
List<HDDDto> findFilteredHDDs(@Param("filters") Map<String, List<String>> filters);
    
}
