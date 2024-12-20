package com.boot.selftop_web.product.biz.mapper;

import com.boot.selftop_web.product.model.dto.CPUDto;
import com.boot.selftop_web.product.model.dto.ProductDto;
import com.boot.selftop_web.product.model.dto.RAMDto;

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
    
    @Select("SELECT COUNT(*) FROM PRODUCT WHERE PRODUCT_CODE = #{productCode}")
    int countByProductCode(@Param("productCode") int productCode);

    // 판매자 제품 등록시 제품 코드의 유효성 검사를 위한 메소드
    default boolean isValidProductCode(int productCode) {
        return countByProductCode(productCode) > 0;
    }
}
