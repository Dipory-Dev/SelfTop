package com.boot.selftop_web.quote.model.dto;

import java.util.Date;

public class CartDTO {
	private int quote_no;
	private int customer_no;
	private String quote_name;
	private Date quote_date;
	private int quote_amount;
	private char assembly;

	private int product_code;
	private int seller_no;
	private int amount;

	public CartDTO() {}

	public CartDTO(int amount, char assembly, int customer_no, int product_code, int quote_amount, Date quote_date, String quote_name, int quote_no, int seller_no) {
		this.amount = amount;
		this.assembly = assembly;
		this.customer_no = customer_no;
		this.product_code = product_code;
		this.quote_amount = quote_amount;
		this.quote_date = quote_date;
		this.quote_name = quote_name;
		this.quote_no = quote_no;
		this.seller_no = seller_no;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public char getAssembly() {
		return assembly;
	}

	public void setAssembly(char assembly) {
		this.assembly = assembly;
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

	public int getQuote_amount() {
		return quote_amount;
	}

	public void setQuote_amount(int quote_amount) {
		this.quote_amount = quote_amount;
	}

	public Date getQuote_date() {
		return quote_date;
	}

	public void setQuote_date(Date quote_date) {
		this.quote_date = quote_date;
	}

	public String getQuote_name() {
		return quote_name;
	}

	public void setQuote_name(String quote_name) {
		this.quote_name = quote_name;
	}

	public int getQuote_no() {
		return quote_no;
	}

	public void setQuote_no(int quote_no) {
		this.quote_no = quote_no;
	}

	public int getSeller_no() {
		return seller_no;
	}

	public void setSeller_no(int seller_no) {
		this.seller_no = seller_no;
	}

	@Override
	public String toString() {
		return "CartDTO{" +
				"amount=" + amount +
				", quote_no=" + quote_no +
				", customer_no=" + customer_no +
				", quote_name='" + quote_name + '\'' +
				", quote_date=" + quote_date +
				", quote_amount=" + quote_amount +
				", assembly=" + assembly +
				", product_code=" + product_code +
				", seller_no=" + seller_no +
				'}';
	}
}
