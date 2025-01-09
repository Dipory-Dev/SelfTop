package com.boot.selftop_web.member.seller.model.dto;

import java.util.Date;

public class SellerOrderDto {
    private Date order_Date;
    private String category;
    private String thumbnail;
    private String product_name;
	private int product_code;
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

	public int getProduct_code() {
		return product_code;
	}

	public void setProduct_code(int product_code) {
		this.product_code = product_code;
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

	@Override
	public String toString() {
		return "SellerOrderDto{" +
				"order_Date=" + order_Date +
				", category='" + category + '\'' +
				", thumbnail='" + thumbnail + '\'' +
				", product_name='" + product_name + '\'' +
				", product_code=" + product_code +
				", amount=" + amount +
				", price=" + price +
				", order_status='" + order_status + '\'' +
				", customer_no=" + customer_no +
				", seller_no=" + seller_no +
				", order_no=" + order_no +
				'}';
	}
}