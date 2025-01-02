package com.boot.selftop_web.product.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;

import java.util.Date;

public class MainBoardDto extends ProductStatusDto implements ProductDto {
    private int product_code;
    private String category;
    private String product_name;
    private String company;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date upload_date;
    private String thumbnail;
    private String content_img;
    private String etc;

    private String socket;
    private String formfactor;
    private int memory_slot;
    private String ddr;
    private int max_storage;
    private int watt;

    public MainBoardDto() {}

    public MainBoardDto(int product_code, String category, String product_name, String company, Date upload_date, String thumbnail, String content_img, String etc, String socket, String formfactor, int memory_slot, String ddr, int max_storage, int watt) {
        this.product_code = product_code;
        this.category = category;
        this.product_name = product_name;
        this.company = company;
        this.upload_date = upload_date;
        this.thumbnail = thumbnail;
        this.content_img = content_img;
        this.etc = etc;
        this.socket = socket;
        this.formfactor = formfactor;
        this.memory_slot = memory_slot;
        this.ddr = ddr;
        this.max_storage = max_storage;
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

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getFormfactor() {
        return formfactor;
    }

    public void setFormfactor(String formfactor) {
        this.formfactor = formfactor;
    }

    public int getMemory_slot() {
        return memory_slot;
    }

    public void setMemory_slot(int memory_slot) {
        this.memory_slot = memory_slot;
    }

    public String getDdr() {
        return ddr;
    }

    public void setDdr(String ddr) {
        this.ddr = ddr;
    }

    public int getMax_storage() {
        return max_storage;
    }

    public void setMax_storage(int max_storage) {
        this.max_storage = max_storage;
    }

    public int getWatt() {
        return watt;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }

    @Override
    public String toString() {
        return "MainBoardDto{" +
                "product_code=" + product_code +
                ", category='" + category + '\'' +
                ", product_name='" + product_name + '\'' +
                ", company='" + company + '\'' +
                ", upload_date=" + upload_date +
                ", thumbnail='" + thumbnail + '\'' +
                ", content_img='" + content_img + '\'' +
                ", etc='" + etc + '\'' +
                ", socket='" + socket + '\'' +
                ", formfactor='" + formfactor + '\'' +
                ", memory_slot=" + memory_slot +
                ", ddr='" + ddr + '\'' +
                ", max_storage=" + max_storage +
                ", watt=" + watt +
                '}';
    }
}
