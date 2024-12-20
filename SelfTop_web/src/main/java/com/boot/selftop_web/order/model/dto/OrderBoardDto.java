package com.boot.selftop_web.order.model.dto;

public class OrderBoardDto {
	private int order_no;
	private int seller_no;
	private int customer_no;
	private String address;
	private String request;
	private String reason;
	private String name;
	private String phone;
	
	public OrderBoardDto() {}
	public OrderBoardDto(int order_no, int seller_no, int customer_no, String address, String request, String reason,
			String name, String phone) {
		super();
		this.order_no = order_no;
		this.seller_no = seller_no;
		this.customer_no = customer_no;
		this.address = address;
		this.request = request;
		this.reason = reason;
		this.name = name;
		this.phone = phone;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public int getSeller_no() {
		return seller_no;
	}
	public void setSeller_no(int seller_no) {
		this.seller_no = seller_no;
	}
	public int getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	

}
