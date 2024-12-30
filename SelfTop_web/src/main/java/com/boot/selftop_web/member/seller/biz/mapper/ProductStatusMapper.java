package com.boot.selftop_web.member.seller.biz.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;

@Mapper
public interface ProductStatusMapper {
    @Insert("INSERT INTO PRODUCT_STATUS (PRODUCT_CODE, SELLER_NO, STOCK, PRICE, REG_DATE) " +
            "VALUES (#{product_code}, #{seller_no}, #{stock}, #{price}, #{reg_date})")
    int insertProductStatus(ProductStatusDto productStatus);

    @Update("UPDATE PRODUCT_STATUS SET PRICE = #{price}, STOCK = #{stock} WHERE PRODUCT_CODE = #{product_code} AND SELLER_NO = #{seller_no}")
    int updateProductStatus(@Param("price") int price, @Param("stock") int stock, @Param("product_code") int productCode, @Param("seller_no") int sellerNo);

    @Delete("DELETE FROM PRODUCT_STATUS WHERE PRODUCT_CODE = #{product_code} AND SELLER_NO = #{seller_no}")
    int deleteProductStatus(@Param("product_code") int productCode, @Param("seller_no") int sellerNo);

}
