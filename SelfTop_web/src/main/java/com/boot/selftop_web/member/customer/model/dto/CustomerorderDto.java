package com.boot.selftop_web.member.customer.model.dto;

import java.util.Date;

public class CustomerorderDto {
	private String thumbnail;
	private Date order_date;
	private String product_name;
	private int price;
	private int order_num;
	private String order_status;
	private int member_no;
	private int item;
	
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public CustomerorderDto(String thumbnail, Date order_date, String product_name, int price, int order_num,
			String order_status, int member_no, int item) {
		super();
		this.thumbnail = thumbnail;
		this.order_date = order_date;
		this.product_name = product_name;
		this.price = price;
		this.order_num = order_num;
		this.order_status = order_status;
		this.member_no = member_no;
		this.item = item;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	
	
	

}
