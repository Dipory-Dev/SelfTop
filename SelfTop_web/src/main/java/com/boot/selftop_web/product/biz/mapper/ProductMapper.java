package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.*;

import java.util.List;
import java.util.Map;

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


    //CPU 필터링
    @Select("SELECT DISTINCT socket FROM cpu")
    List<String> findAllcpuSocket();
    @Select("SELECT DISTINCT ddr FROM cpu")
    List<String> findAllcpuDdr();
    @Select("SELECT DISTINCT generation FROM cpu")
    List<String> findAllcpuGeneration();
    @Select("SELECT DISTINCT spec FROM cpu")
    List<String> findAllcpuSpec();
    @Select("SELECT DISTINCT inner_vga FROM cpu")
    List<String> findAllcpuInnerVga();
    @Select("SELECT DISTINCT package_type FROM cpu")
    List<String> findAllcpuPackageType();
    @Select("SELECT DISTINCT cooler_status FROM cpu")
    List<String> findAllcpuCoolerStatus();
    @Select("SELECT DISTINCT core FROM cpu")
    List<String> findAllcpuCore();
    @Select("SELECT DISTINCT p.company FROM product p JOIN cpu c ON p.product_code = c.product_code WHERE p.category = 'CPU'")
    List<String> findAllcpuCompany();

    //RAM 필터링
    @Select("SELECT DISTINCT ddr FROM ram")
    List<String> findAllramDdr();
    @Select("SELECT DISTINCT storage FROM ram")
    List<String> findAllramStorage();
    @Select("SELECT DISTINCT device FROM ram")
    List<String> findAllramDevice();
    @Select("SELECT DISTINCT heatsync FROM ram")
    List<String> findAllramHeatsync();
    @Select("SELECT DISTINCT p.company FROM product p JOIN ram r ON p.product_code = r.product_code WHERE p.category = 'RAM'")
    List<String> findAllramCompany();

    //SSD 필터링
    @Select("SELECT DISTINCT storage FROM ssd")
    List<String> findAllssdStorage();
    @Select("SELECT DISTINCT type FROM ssd")
    List<String> findAllssdType();
    @Select("SELECT DISTINCT p.company FROM product p JOIN ssd s ON p.product_code = s.product_code WHERE p.category = 'SSD'")
    List<String> findAllssdCompany();

    //파워 필터링
    @Select("SELECT DISTINCT supply FROM power")
    List<String> findAllpowerSupply();
    @Select("SELECT DISTINCT plus80 FROM power")
    List<String> findAllpowerPlus80();
    @Select("SELECT DISTINCT formfactor FROM power")
    List<String> findAllpowerFormfactor();
    @Select("SELECT DISTINCT p.company FROM product p JOIN power pw ON p.product_code = pw.product_code WHERE p.category = '파워'")
    List<String> findAllpowerCompany();

    //쿨러 필터링
    @Select("SELECT DISTINCT cooler_type FROM cooler")
    List<String> findAllcoolerCooler_Type();
    @Select("SELECT DISTINCT socket FROM cooler")
    List<String> findAllcoolerSocket();
    @Select("SELECT DISTINCT p.company FROM product p JOIN cooler c ON p.product_code = c.product_code WHERE p.category = '쿨러'")
    List<String> findAllcoolerCompany();

    //메인보드 필터링
    @Select("SELECT DISTINCT Socket FROM mainboard")
    List<String> findAllmainboardSocket();
    @Select("SELECT DISTINCT formfactor FROM mainboard")
    List<String> findAllmainboardFormfactor();
    @Select("SELECT DISTINCT memory_slot FROM mainboard")
    List<String> findAllmainboardMemory_Slot();
    @Select("SELECT DISTINCT ddr FROM mainboard")
    List<String> findAllmainboardDdr();
    @Select("SELECT DISTINCT max_storage FROM mainboard")
    List<String> findAllmainboardMax_Storage();
    @Select("SELECT DISTINCT p.company FROM product p JOIN mainboard mb ON p.product_code = mb.product_code WHERE p.category = '메인보드'")
    List<String> findAllmainboardCompany();

    //그래픽카드 필터링
    @Select("SELECT DISTINCT series FROM gpu")
    List<String> findAllgpuSeries();
    @Select("SELECT DISTINCT storage FROM gpu")
    List<String> findAllgpuStorage();
    @Select("SELECT DISTINCT length FROM gpu")
    List<String> findAllgpuLength();
    @Select("SELECT DISTINCT p.company FROM product p JOIN gpu g ON p.product_code = g.product_code WHERE p.category = '그래픽카드'")
    List<String> findAllgpuCompany();

    //HDD 필터링
    @Select("SELECT DISTINCT device FROM hdd")
    List<String> findAllhddDevice();
    @Select("SELECT DISTINCT Storage FROM hdd")
    List<String> findAllhddStorage();
    @Select("SELECT DISTINCT p.company FROM product p JOIN hdd h ON p.product_code = h.product_code WHERE p.category = 'HDD'")
    List<String> findAllhddCompany();

    //케이스 필터링
    @Select("SELECT DISTINCT power_status FROM case_board")
    List<String> findAllcasePower_Status();
    @Select("SELECT DISTINCT formfactor FROM case_board")
    List<String> findAllcaseFormfactor();
    @Select("SELECT DISTINCT tower_size FROM case_board")
    List<String> findAllcaseTower_Size();
    @Select("SELECT DISTINCT vga_length FROM case_board")
    List<String> findAllcaseVga_Length();
    @Select("SELECT DISTINCT power_size FROM case_board")
    List<String> findAllcasePower_Size();
    @Select("SELECT DISTINCT p.company FROM product p JOIN case_board cb ON p.product_code = cb.product_code WHERE p.category = '케이스'")
    List<String> findAllcaseCompany();

    //product테이블에서 가져오는 쿼리문
    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<CPUDto> findAllDetailedCpuProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);


    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<CoolerDto> findAllDetailedCoolerProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<CaseDto> findAllDetailedCaseProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<GPUDto> findAllDetailedGpuProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<HDDDto> findAllDetailedHddProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<PowerDto> findAllDetailedPowerProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<RAMDto> findAllDetailedRamProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<SSDDto> findAllDetailedSsdProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);

    @Select("<script>" +
            "SELECT p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, " +
            "MIN(ps.PRICE) AS price, " +
            "ps_min.STOCK, " +
            "ps_min.SELLER_NO, " +

            // 가중평점 계산
            "COUNT(r.REVIEW_NO) AS review_count, " +
            "AVG(r.RATING) AS average_rating, " +
            "( " +
            "    (COUNT(r.REVIEW_NO) * AVG(r.RATING)) + " +
            "    (30 * (" +
            "        SELECT AVG(r_sub.RATING) " +
            "        FROM REVIEW r_sub " +
            "        JOIN PRODUCT p_sub ON r_sub.PRODUCT_CODE = p_sub.PRODUCT_CODE " +
            "        WHERE p_sub.CATEGORY = #{category}" +
            "    )) " +
            ") / (COUNT(r.REVIEW_NO) + 30) AS weighted_rating " +

            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_STATUS ps ON p.PRODUCT_CODE = ps.PRODUCT_CODE " +

            // 리뷰 테이블과 조인
            "LEFT JOIN REVIEW r ON p.PRODUCT_CODE = r.PRODUCT_CODE " +

            "LEFT JOIN (" +
            "    SELECT ps_inner.PRODUCT_CODE, ps_inner.STOCK, ps_inner.SELLER_NO " +
            "    FROM PRODUCT_STATUS ps_inner " +
            "    WHERE ps_inner.PRICE = (" +
            "        SELECT MIN(ps_sub.PRICE) " +
            "        FROM PRODUCT_STATUS ps_sub " +
            "        WHERE ps_sub.PRODUCT_CODE = ps_inner.PRODUCT_CODE" +
            "    )" +
            ") ps_min ON p.PRODUCT_CODE = ps_min.PRODUCT_CODE " +
            "WHERE p.CATEGORY = #{category} " +
            "<if test='searchTerm != null'>" +
            "AND (LOWER(p.PRODUCT_NAME) LIKE '%' || LOWER(#{searchTerm}) || '%' OR LOWER(p.ETC) LIKE '%' || LOWER(#{searchTerm}) || '%')" +
            "</if>" +
            "GROUP BY p.PRODUCT_CODE, p.PRODUCT_NAME, p.ETC, p.THUMBNAIL, ps_min.STOCK, ps_min.SELLER_NO " +
            "<if test='sort != null'>ORDER BY " +
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
    List<MainBoardDto> findAllDetailedMainBoardProducts(@Param("category") String category, @Param("sort") String sort, @Param("searchTerm") String searchTerm);


    @Select("Select * from product where product_code=#{product_code}")
    ProductInfoDto selectOne(@Param("product_code") int product_code);

}
