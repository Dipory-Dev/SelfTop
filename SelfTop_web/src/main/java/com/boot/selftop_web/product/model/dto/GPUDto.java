package com.boot.selftop_web.product.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class GPUDto implements ProductDto {
    private int product_code;
    private String category;
    private String product_name;
    private String company;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date upload_date;
    private String thumbnail;
    private String content_img;
    private String etc;

    private String series;
    private int storage;
    private int length;
    private int watt;

    public GPUDto() {}

    public GPUDto(int product_code, String category, String product_name, String company, Date upload_date, String thumbnail, String content_img, String etc, String series, int storage, int length, int watt) {
        this.product_code = product_code;
        this.category = category;
        this.product_name = product_name;
        this.company = company;
        this.upload_date = upload_date;
        this.thumbnail = thumbnail;
        this.content_img = content_img;
        this.etc = etc;
        this.series = series;
        this.storage = storage;
        this.length = length;
        this.watt = watt;
    }

    public int getProduct_code() {
        return product_code;
    }

    public void setProduct_code(int product_code) {
        this.product_code = product_code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWatt() {
        return watt;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }

    @Override
    public String toString() {
        return "GPUDto{" +
                "product_code=" + product_code +
                ", category='" + category + '\'' +
                ", product_name='" + product_name + '\'' +
                ", company='" + company + '\'' +
                ", upload_date=" + upload_date +
                ", thumbnail='" + thumbnail + '\'' +
                ", content_img='" + content_img + '\'' +
                ", etc='" + etc + '\'' +
                ", series='" + series + '\'' +
                ", storage=" + storage +
                ", length=" + length +
                ", watt=" + watt +
                '}';
    }
}