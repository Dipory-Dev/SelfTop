package com.boot.selftop_web.member.seller.model.dto;

import java.util.Date;

public class SellerOrderDto {
    private Date order_Date;
    private String category;
    private String thumbnail;
    private String product_name;
    private int amount;
    private int price;
    private String order_status;
    private int customer_no;
    private int seller_no;
    private int order_no;
    
	public Date getOrder_Date() {
		return order_Date;
	}
	public void setOrder_Date(Date order_Date) {
		this.order_Date = order_Date;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public int getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}
	public int getSeller_no() {
		return seller_no;
	}
	public void setSeller_no(int seller_no) {
		this.seller_no = seller_no;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public SellerOrderDto(Date order_Date, String category, String thumbnail, String product_name, int amount,
			int price, String order_status, int customer_no, int seller_no, int order_no) {
		super();
		this.order_Date = order_Date;
		this.category = category;
		this.thumbnail = thumbnail;
		this.product_name = product_name;
		this.amount = amount;
		this.price = price;
		this.order_status = order_status;
		this.customer_no = customer_no;
		this.seller_no = seller_no;
		this.order_no = order_no;
	}

   
}