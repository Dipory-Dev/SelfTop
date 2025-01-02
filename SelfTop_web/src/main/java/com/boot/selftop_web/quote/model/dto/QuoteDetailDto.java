package com.boot.selftop_web.quote.model.dto;

public class QuoteDetailDto {
	private int quote_no;
	private int customer_no;
	private int product_code;
	private int seller_no;
	private int amount;
	private String Category;
	private String Thumbnail;
	private String product_name;
	private int price;

	public QuoteDetailDto() {}

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

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getThumbnail() {
		return Thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		Thumbnail = thumbnail;
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

	public QuoteDetailDto(int quote_no, int customer_no, int product_code, int seller_no, int amount, String category,
			String thumbnail, String product_name, int price) {
		super();
		this.quote_no = quote_no;
		this.customer_no = customer_no;
		this.product_code = product_code;
		this.seller_no = seller_no;
		this.amount = amount;
		Category = category;
		Thumbnail = thumbnail;
		this.product_name = product_name;
		this.price = price;
	}
	
	
}
