package com.boot.selftop_web.seller.model.dto;

public class SellerStockDto {
	private String category;
	private String product_name;
	private String Thumbnail;
	private int product_code;
	private int seller_no;
	private int stock;
	private int price;
	
	public SellerStockDto() {};
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getThumbnail() {
		return Thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		Thumbnail = thumbnail;
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

	public SellerStockDto(String category, String product_name, String thumbnail, int product_code, int seller_no,
			int stock, int price) {
		super();
		this.category = category;
		this.product_name = product_name;
		Thumbnail = thumbnail;
		this.product_code = product_code;
		this.seller_no = seller_no;
		this.stock = stock;
		this.price = price;
	}
	

}
