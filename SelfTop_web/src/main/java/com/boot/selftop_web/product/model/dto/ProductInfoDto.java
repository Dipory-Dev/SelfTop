package com.boot.selftop_web.product.model.dto;

import java.util.Date;

public class ProductInfoDto {
    private int product_code;
    private String category;
    private String product_name;
    private String company;
    private Date upload_date;
    private String thumbnail;
    private String content_img;
    private String etc;

    public ProductInfoDto() {}

    public ProductInfoDto(String category, String company, String content_img, String etc, int product_code, String product_name, String thumbnail, Date upload_date) {
        this.category = category;
        this.company = company;
        this.content_img = content_img;
        this.etc = etc;
        this.product_code = product_code;
        this.product_name = product_name;
        this.thumbnail = thumbnail;
        this.upload_date = upload_date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContent_img() {
        return content_img;
    }

    public void setContent_img(String content_img) {
        this.content_img = content_img;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public int getProduct_code() {
        return product_code;
    }

    public void setProduct_code(int product_code) {
        this.product_code = product_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    @Override
    public String toString() {
        return "ProductInfoDto{" +
                "category='" + category + '\'' +
                ", product_code=" + product_code +
                ", product_name='" + product_name + '\'' +
                ", company='" + company + '\'' +
                ", upload_date=" + upload_date +
                ", thumbnail='" + thumbnail + '\'' +
                ", content_img='" + content_img + '\'' +
                ", etc='" + etc + '\'' +
                '}';
    }
}
