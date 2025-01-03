package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.MainBoardDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MainBoardMapper {
    @Insert("INSERT INTO MAINBOARD (PRODUCT_CODE, SOCKET, FORMFACTOR, MEMORY_SLOT, DDR, MAX_STORAGE, WATT) " +
            "VALUES (#{product_code}, #{socket}, #{formfactor}, #{memory_slot}, #{ddr}, #{max_storage}, #{watt})")
    int insertMainBoard(MainBoardDto dto);
    
    @Select("<script>" + 
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC, MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "JOIN MAINBOARD m ON p.PRODUCT_CODE = m.PRODUCT_CODE " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = '메인보드' " +
            "<if test='filters.Company != null'>" +
            "   AND p.COMPANY IN " +
            "   <foreach item='company' collection='filters.Company' open='(' separator=',' close=')'>" +
            "       #{company}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Socket != null'>" +
            "   AND m.SOCKET IN " +
            "   <foreach item='socket' collection='filters.Socket' open='(' separator=',' close=')'>" +
            "       #{socket}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.DDR != null'>" +
            "   AND m.DDR IN " +
            "   <foreach item='ddr' collection='filters.DDR' open='(' separator=',' close=')'>" +
            "       #{ddr}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Formfactor != null'>" +
            "   AND m.FORMFACTOR IN " +
            "   <foreach item='formfactor' collection='filters.Formfactor' open='(' separator=',' close=')'>" +
            "       #{formfactor}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Memory_Slot != null'>" +
            "   AND m.MEMORY_SLOT IN " +
            "   <foreach item='memorySlot' collection='filters.Memory_Slot' open='(' separator=',' close=')'>" +
            "       #{memorySlot}" +
            "   </foreach>" +
            "</if>" +
            "<if test='filters.Max_Storage != null'>" +
            "   AND m.MAX_STORAGE IN " +
            "   <foreach item='maxStorage' collection='filters.Max_Storage' open='(' separator=',' close=')'>" +
            "       #{maxStorage}" +
            "   </foreach>" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.THUMBNAIL, p.ETC" +
            "</script>")
    List<MainBoardDto> findFilteredMainBoards(@Param("filters") Map<String, List<String>> filters);
}
