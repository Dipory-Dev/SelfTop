package com.boot.selftop_web.seller.model.mapper.product;

import com.boot.selftop_web.seller.model.dto.product.GPUDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GPUMapper {
    @Insert("INSERT INTO PRODUCT (PRODUCT_CODE, CATEGORY, PRODUCT_NAME, COMPANY, UPLOAD_DATE, THUMBNAIL, CONTENT_IMG, ETC) " +
            "VALUES (SEQ_PRODUCT.NEXTVAL, #{category}, #{product_name}, #{company}, #{upload_date}, #{thumbnail}, #{content_img}, #{etc})")
    int insertProduct(GPUDto dto);

    @Select("SELECT SEQ_PRODUCT.CURRVAL FROM DUAL")
    int getCurrentProductCode();

    @Insert("INSERT INTO GPU (PRODUCT_CODE, SERIES, STORAGE, LENGTH, WATT) " +
            "VALUES (#{product_code}, #{series}, #{storage}, #{length}, #{watt})")
    int insertGPU(GPUDto dto);
}
