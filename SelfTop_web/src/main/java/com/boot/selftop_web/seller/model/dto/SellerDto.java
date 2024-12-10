package com.boot.selftop_web.seller.model.dto;

import java.util.Date;

public class SellerDto {
    private Date order_Date;       
    private String cartegory;      
    private String p_Image;   
    private String p_model;
    private int amount;          
    private int price;         
    private String status;        
    private String c_Inform;    
    
    
    public SellerDto() {}


	public SellerDto(Date order_Date, String cartegory, String p_Image,String p_model, int amount, int price, String status,
			String c_Inform) {
		super();
		this.order_Date = order_Date;
		this.cartegory = cartegory;
		this.p_Image = p_Image;
		this.p_model = p_model;
		this.amount = amount;
		this.price = price;
		this.status = status;
		this.c_Inform = c_Inform;
	}


	public Date getOrder_Date() {
		return order_Date;
	}


	public void setOrder_Date(Date order_Date) {
		this.order_Date = order_Date;
	}


	public String getCartegory() {
		return cartegory;
	}


	public void setCartegory(String cartegory) {
		this.cartegory = cartegory;
	}


	public String getP_Image() {
		return p_Image;
	}


	public void setP_Image(String p_Image) {
		this.p_Image = p_Image;
	}
	public String getP_model() {
		return p_model;
	}
	
	
	public void setP_model(String p_model) {
		this.p_model = p_model;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getC_Inform() {
		return c_Inform;
	}


	public void setC_Inform(String c_Inform) {
		this.c_Inform = c_Inform;
	}
    
    
	
}
