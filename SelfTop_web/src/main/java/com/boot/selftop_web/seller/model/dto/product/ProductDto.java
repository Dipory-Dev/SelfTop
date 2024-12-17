package com.boot.selftop_web.seller.model.dto.product;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface ProductDto {
    int getProduct_code();
    void setProduct_code(int product_code);

    String getCategory();
    void setCategory(String category);

    String getProduct_name();
    void setProduct_name(String product_name);

    String getCompany();
    void setCompany(String company);

    @DateTimeFormat(pattern = "yyyy-MM")
    Date getUpload_date();
    void setUpload_date(Date upload_date);

    String getThumbnail();
    void setThumbnail(String thumbnail);

    String getContent_img();
    void setContent_img(String content_img);

    String getEtc();
    void setEtc(String etc);

}
