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
}
