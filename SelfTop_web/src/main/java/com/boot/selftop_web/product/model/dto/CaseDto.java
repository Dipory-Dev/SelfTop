package com.boot.selftop_web.product.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CaseDto implements ProductDto{
    private int product_code;
    private String category;
    private String product_name;
    private String company;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date upload_date;
    private String thumbnail;
    private String content_img;
    private String etc;


    private String power_status;
    private String formfactor;
    private String tower_size;
    private int vga_length;

    public CaseDto() {}

    public CaseDto(int product_code, String category, String product_name, String company, Date upload_date, String thumbnail, String content_img, String etc, String power_status, String formfactor, String tower_size, int vga_length) {
        this.product_code = product_code;
        this.category = category;
        this.product_name = product_name;
        this.company = company;
        this.upload_date = upload_date;
        this.thumbnail = thumbnail;
        this.content_img = content_img;
        this.etc = etc;
        this.power_status = power_status;
        this.formfactor = formfactor;
        this.tower_size = tower_size;
        this.vga_length = vga_length;
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

    public String getPower_status() {
        return power_status;
    }

    public void setPower_status(String power_status) {
        this.power_status = power_status;
    }

    public String getFormfactor() {
        return formfactor;
    }

    public void setFormfactor(String formfactor) {
        this.formfactor = formfactor;
    }

    public String getTower_size() {
        return tower_size;
    }

    public void setTower_size(String tower_size) {
        this.tower_size = tower_size;
    }

    public int getVga_length() {
        return vga_length;
    }

    public void setVga_length(int vga_length) {
        this.vga_length = vga_length;
    }

    @Override
    public String toString() {
        return "CaseDto{" +
                "product_code=" + product_code +
                ", category='" + category + '\'' +
                ", product_name='" + product_name + '\'' +
                ", company='" + company + '\'' +
                ", upload_date=" + upload_date +
                ", thumbnail='" + thumbnail + '\'' +
                ", content_img='" + content_img + '\'' +
                ", etc='" + etc + '\'' +
                ", power_status='" + power_status + '\'' +
                ", formfactor='" + formfactor + '\'' +
                ", tower_size='" + tower_size + '\'' +
                ", vga_length=" + vga_length +
                '}';
    }
}
