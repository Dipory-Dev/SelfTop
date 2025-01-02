package com.boot.selftop_web.product.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;

import java.util.Date;

public class CPUDto extends ProductStatusDto implements ProductDto {
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
    private String ddr;
    private String generation;
    private String spec;
    private String inner_vga;
    private String package_type;
    private String cooler_status;
    private int core;
    private int watt;    
    
    
    public CPUDto() {}

    

    public CPUDto(int product_code, String category, String product_name, String company, Date upload_date,
			String thumbnail, String content_img, String etc, String socket, String ddr, String generation, String spec,
			String inner_vga, String package_type, String cooler_status, int core, int watt) {
		super();
		this.product_code = product_code;
		this.category = category;
		this.product_name = product_name;
		this.company = company;
		this.upload_date = upload_date;
		this.thumbnail = thumbnail;
		this.content_img = content_img;
		this.etc = etc;
		this.socket = socket;
		this.ddr = ddr;
		this.generation = generation;
		this.spec = spec;
		this.inner_vga = inner_vga;
		this.package_type = package_type;
		this.cooler_status = cooler_status;
		this.core = core;
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

    public String getDdr() {
        return ddr;
    }

    public void setDdr(String ddr) {
        this.ddr = ddr;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getInner_vga() {
        return inner_vga;
    }

    public void setInner_vga(String inner_vga) {
        this.inner_vga = inner_vga;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getCooler_status() {
        return cooler_status;
    }

    public void setCooler_status(String cooler_status) {
        this.cooler_status = cooler_status;
    }

    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public int getWatt() {
        return watt;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }


	@Override
    public String toString() {
        return "CPUDto{" +
                "product_code=" + product_code +
                ", category='" + category + '\'' +
                ", product_name='" + product_name + '\'' +
                ", company='" + company + '\'' +
                ", upload_date=" + upload_date +
                ", thumbnail='" + thumbnail + '\'' +
                ", content_img='" + content_img + '\'' +
                ", etc='" + etc + '\'' +
                ", socket='" + socket + '\'' +
                ", ddr='" + ddr + '\'' +
                ", generation='" + generation + '\'' +
                ", spec='" + spec + '\'' +
                ", inner_vga='" + inner_vga + '\'' +
                ", package_type='" + package_type + '\'' +
                ", cooler_status='" + cooler_status + '\'' +
                ", core=" + core +
                ", watt=" + watt +
                '}';
    }
}
