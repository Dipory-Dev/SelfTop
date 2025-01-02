package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CPUDto;
import com.boot.selftop_web.product.model.dto.CaseDto;
import com.boot.selftop_web.product.model.dto.CoolerDto;
import com.boot.selftop_web.product.model.dto.GPUDto;
import com.boot.selftop_web.product.model.dto.HDDDto;
import com.boot.selftop_web.product.model.dto.MainBoardDto;
import com.boot.selftop_web.product.model.dto.PowerDto;
import com.boot.selftop_web.product.model.dto.ProductDto;
import com.boot.selftop_web.product.model.dto.RAMDto;
import com.boot.selftop_web.product.model.dto.SSDDto;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper {
    @Insert("INSERT INTO PRODUCT (PRODUCT_CODE, CATEGORY, PRODUCT_NAME, COMPANY, UPLOAD_DATE, THUMBNAIL, CONTENT_IMG, ETC) " +
            "VALUES (SEQ_PRODUCT.NEXTVAL, #{category}, #{product_name}, #{company}, #{upload_date}, #{thumbnail}, #{content_img}, #{etc})")
    int insertProduct(ProductDto dto);

    @Select("SELECT SEQ_PRODUCT.CURRVAL FROM DUAL")
    int getCurrentProductCode();
    
    //판매자가 제품을 등록할때 필요
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN CPU c ON p.PRODUCT_CODE = c.PRODUCT_CODE")
    List<CPUDto> findAllCpuProducts();
    
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN Ram r ON p.PRODUCT_CODE = r.PRODUCT_CODE")
    List<RAMDto> findAllRamProducts();
    
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN MAINBOARD mb ON p.PRODUCT_CODE = mb.PRODUCT_CODE")
    List<MainBoardDto> findAllMainBoardProducts();
    
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN CASE_BOARD cb ON p.PRODUCT_CODE = cb.PRODUCT_CODE")
    List<CaseDto> findAllCaseProducts();
    
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN GPU g ON p.PRODUCT_CODE = g.PRODUCT_CODE")
    List<GPUDto> findAllGpuProducts();
    
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN POWER pw ON p.PRODUCT_CODE = pw.PRODUCT_CODE")
    List<PowerDto> findAllPowerProducts();
    
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN SSD s ON p.PRODUCT_CODE = s.PRODUCT_CODE")
    List<SSDDto> findAllSsdProducts();
    
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN HDD h ON p.PRODUCT_CODE = h.PRODUCT_CODE")
    List<HDDDto> findAllHddProducts();
    
    @Select("SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC FROM PRODUCT p JOIN COOLER cl ON p.PRODUCT_CODE = cl.PRODUCT_CODE")
    List<CoolerDto> findAllCoolerProducts();
    
    @Select("SELECT COUNT(*) FROM PRODUCT WHERE PRODUCT_CODE = #{productCode}")
    int countByProductCode(@Param("productCode") int productCode);

    // 판매자 제품 등록시 제품 코드의 유효성 검사를 위한 메소드
    default boolean isValidProductCode(int productCode) {
        return countByProductCode(productCode) > 0;
    }

    //product테이블에서 가져오는 쿼리문
    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<CPUDto> findAllDetailedCpuProducts(@Param("category") String category, @Param("sort") String sort);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<CoolerDto> findAllDetailedCoolerProducts(@Param("category") String category, @Param("sort") String sort);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<CaseDto> findAllDetailedCaseProducts(@Param("category") String category, @Param("sort") String sort);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<GPUDto> findAllDetailedGpuProducts(@Param("category") String category, @Param("sort") String sort);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<HDDDto> findAllDetailedHddProducts(@Param("category") String category, @Param("sort") String sort);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<PowerDto> findAllDetailedPowerProducts(@Param("category") String category, @Param("sort") String sort);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<RAMDto> findAllDetailedRamProducts(@Param("category") String category, @Param("sort") String sort);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<SSDDto> findAllDetailedSsdProducts(@Param("category") String category, @Param("sort") String sort);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL " +
            "<if test='sort != null'>ORDER BY " +
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
    List<MainBoardDto> findAllDetailedMainBoardProducts(@Param("category") String category, @Param("sort") String sort);

}
