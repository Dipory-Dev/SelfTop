package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CPUDto;
import com.boot.selftop_web.product.model.dto.CaseDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CaseMapper {
    @Insert("INSERT INTO CASE_BOARD (PRODUCT_CODE, POWER_STATUS, FORMFACTOR, TOWER_SIZE, VGA_LENGTH) " +
            "VALUES (#{product_code}, #{power_status}, #{formfactor}, #{tower_size}, #{vga_length})")
    int insertCase_board(CaseDto dto);
    
    @Select("<script>" + 
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN CASE_BOARD c ON p.PRODUCT_CODE = c.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = '케이스' " +
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
            "   AND c.FORMFACTOR IN " +
            "   <foreach item='formfactor' collection='filters.Formfactor' open='(' separator=',' close=')'>" +
            "       #{formfactor}" +
            "   </foreach>" +
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
            "   AND c.POWER_SIZE IN " +
            "   <foreach item='powerSize' collection='filters.Power_Size' open='(' separator=',' close=')'>" +
            "       #{powerSize}" +
            "   </foreach>" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC" +
            "</script>")
    List<CaseDto> findFilteredCases(@Param("filters") Map<String, List<String>> filters);
    
}
