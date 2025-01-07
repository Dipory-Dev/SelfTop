package com.boot.selftop_web.quote.model.dto;

public class QuotecomparisonDto {
	private int quote_no;
	private int amount;
	private String Category;
	private String product_name;
	private int price;
	private String quote_name;
	
	
	
	public QuotecomparisonDto() {}
	public QuotecomparisonDto(int quote_no, int amount, String category, String product_name, int price,
			String quote_name) {
		super();
		this.quote_no = quote_no;
		this.amount = amount;
		Category = category;
		this.product_name = product_name;
		this.price = price;
		this.quote_name = quote_name;
	}
	public int getQuote_no() {
		return quote_no;
	}
	public void setQuote_no(int quote_no) {
		this.quote_no = quote_no;
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
	public String getQuote_name() {
		return quote_name;
	}
	public void setQuote_name(String quote_name) {
		this.quote_name = quote_name;
	}
	@Override
	public String toString() {
		return "QuotecomparisonDto [quote_no=" + quote_no + ", amount=" + amount + ", Category=" + Category
				+ ", product_name=" + product_name + ", price=" + price + ", quote_name=" + quote_name + "]";
	}
	
	
	
}
