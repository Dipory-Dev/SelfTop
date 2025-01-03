package com.boot.selftop_web.quote.model.dto;

public class CartItemDTO {
	private String name;
    private int price;
    private int quantity;
    private String productCode; // productCode 필드 추가
    
    
	public CartItemDTO(){}
	
	public CartItemDTO(String name, int price, int quantity, String productCode) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.productCode = productCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
