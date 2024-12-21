package com.boot.selftop_web.member.seller.model.dto;

import java.util.Date;

public class ProductStatusDto {
	private int product_code;
	private int seller_no;
	private int stock;
	private int price;
	private Date reg_date;
	
	public ProductStatusDto() {}

	public ProductStatusDto(int product_code, int seller_no, int stock, int price, Date reg_date) {
		super();
		this.product_code = product_code;
		this.seller_no = seller_no;
		this.stock = stock;
		this.price = price;
		this.reg_date = reg_date;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	};
	
	
}
