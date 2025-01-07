package com.boot.selftop_web.order.model.dto;

public class OrderDetailDto {
	private int order_no;
	private int customer_no;
	private int product_code;
	private int seller_no;
	private int amount;
	private int order_price;
	
	public OrderDetailDto() {}

	public OrderDetailDto(int order_no, int customer_no, int product_code, int seller_no, int amount, int order_price) {
		super();
		this.order_no = order_no;
		this.customer_no = customer_no;
		this.product_code = product_code;
		this.seller_no = seller_no;
		this.amount = amount;
		this.order_price = order_price;
	}

	public int getOrder_no() {
		return order_no;
	}

	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}

	public int getCustomer_no() {
		return customer_no;
	}

	public void setCustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}

	public int getProduct_code() {
		return product_code;
	}

	public void setProduct_code(int product_code) {
		this.product_code = product_code;
	}

	public int getSeller_no() {
		return seller_no;
	}

	public void setSeller_no(int seller_no) {
		this.seller_no = seller_no;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getOrder_price() {
		return order_price;
	}

	public void setOrder_price(int order_price) {
		this.order_price = order_price;
	}
	
}
