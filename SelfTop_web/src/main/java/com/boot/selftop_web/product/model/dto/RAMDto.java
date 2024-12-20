package com.boot.selftop_web.product.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RAMDto implements ProductDto {
    private int product_code;
    private String category;
    private String product_name;
    private String company;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date upload_date;
    private String thumbnail;
    private String content_img;
    private String etc;

    private String ddr;
    private int storage;
    private String device;
    private String heatsync;
    private int watt;

    public RAMDto() {}

    public RAMDto(int product_code, String category, String product_name, String company, Date upload_date, String thumbnail, String content_img, String etc, String ddr, int storage, String device, String heatsync, int watt) {
        this.product_code = product_code;
        this.category = category;
        this.product_name = product_name;
        this.company = company;
        this.upload_date = upload_date;
        this.thumbnail = thumbnail;
        this.content_img = content_img;
        this.etc = etc;
        this.ddr = ddr;
        this.storage = storage;
        this.device = device;
        this.heatsync = heatsync;
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

    public String getDdr() {
        return ddr;
    }

    public void setDdr(String ddr) {
        this.ddr = ddr;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getHeatsync() {
        return heatsync;
    }

    public void setHeatsync(String heatsync) {
        this.heatsync = heatsync;
    }

    public int getWatt() {
        return watt;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }

    @Override
    public String toString() {
        return "RAMDto{" +
                "product_code=" + product_code +
                ", category='" + category + '\'' +
                ", product_name='" + product_name + '\'' +
                ", company='" + company + '\'' +
                ", upload_date=" + upload_date +
                ", thumbnail='" + thumbnail + '\'' +
                ", content_img='" + content_img + '\'' +
                ", etc='" + etc + '\'' +
                ", ddr='" + ddr + '\'' +
                ", storage=" + storage +
                ", device='" + device + '\'' +
                ", heatsync='" + heatsync + '\'' +
                ", watt=" + watt +
                '}';
    }
}