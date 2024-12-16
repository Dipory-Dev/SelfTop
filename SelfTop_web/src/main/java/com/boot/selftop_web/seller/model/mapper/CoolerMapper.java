package com.boot.selftop_web.seller.model.mapper;

import com.boot.selftop_web.seller.model.dto.product.CoolerDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CoolerMapper {
    @Insert("INSERT INTO PRODUCT (PRODUCT_CODE, CATEGORY, PRODUCT_NAME, COMPANY, UPLOAD_DATE, THUMBNAIL, CONTENT_IMG, ETC) " +
            "VALUES (SEQ_PRODUCT.NEXTVAL, #{category}, #{product_name}, #{company}, #{upload_date}, #{thumbnail}, #{content_img}, #{etc})")
    int insertProduct(CoolerDto dto);

    @Select("SELECT SEQ_PRODUCT.CURRVAL FROM DUAL")
    int getCurrentProductCode();

    @Insert("INSERT INTO COOLER (PRODUCT_CODE, COOLER_TYPE, SOCKET, WATT) " +
            "VALUES (#{product_code}, #{cooler_type}, #{socket}, #{watt})")
    int insertCooler(CoolerDto dto);
}
