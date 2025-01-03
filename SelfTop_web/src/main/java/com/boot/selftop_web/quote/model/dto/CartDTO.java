package com.boot.selftop_web.quote.model.dto;

import java.util.List;

public class CartDTO {
	
	private String quoteName;
	
	private List<CartItemDTO> items;
	
	public CartDTO() {}

	
	public CartDTO(String quoteName, List<CartItemDTO> items) {
		super();
		this.setQuoteName(quoteName);
		this.setItems(items);
	 }

	public String getQuoteName() {
		return quoteName;
	 }

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	 }

	public List<CartItemDTO> getItems() {
		return items;
	 }

	public void setItems(List<CartItemDTO> items) {
		this.items = items;
	 }
	 
}
