package com.boot.selftop_web.quote.model.dto;

import java.util.Date;

public class QuoteDto {
	private int quote_no;
	private int customer_no;
	private String  quote_name;
	private Date quote_date;
	private int quote_amount;
	private int price;
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public QuoteDto() {}

	public int getQuote_no() {
		return quote_no;
	}

	public void setQuote_no(int quote_no) {
		this.quote_no = quote_no;
	}

	public int getCustomer_no() {
		return customer_no;
	}

	public void setCustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}

	public String getQuote_name() {
		return quote_name;
	}

	public void setQuote_name(String quote_name) {
		this.quote_name = quote_name;
	}

	public Date getQuote_date() {
		return quote_date;
	}

	public void setQuote_date(Date quote_date) {
		this.quote_date = quote_date;
	}

	public int getQuote_amount() {
		return quote_amount;
	}

	public void setQuote_amount(int quote_amount) {
		this.quote_amount = quote_amount;
	}

	public QuoteDto(int quote_no, int customer_no, String quote_name, Date quote_date, int quote_amount,int price) {
		super();
		this.quote_no = quote_no;
		this.customer_no = customer_no;
		this.quote_name = quote_name;
		this.quote_date = quote_date;
		this.quote_amount = quote_amount;
		this.price=price;
	}
	

}
