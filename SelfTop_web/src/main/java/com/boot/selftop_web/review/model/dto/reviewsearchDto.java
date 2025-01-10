package com.boot.selftop_web.review.model.dto;

import java.util.Date;

public class reviewsearchDto {
	private int review_no;
	private String review_img;
	private String content;
	private int rating;
	private int product_code;
	private int member_no;
	private Date created_date;
	
	
	public reviewsearchDto() {}
	
	public int getReview_no() {
		return review_no;
	}
	
	public void setReview_no(int review_no) {
		this.review_no = review_no;
	}
	public String getReview_img() {
		return review_img;
	}
	public void setReview_img(String review_img) {
		this.review_img = review_img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getProduct_code() {
		return product_code;
	}
	public void setProduct_code(int product_code) {
		this.product_code = product_code;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public reviewsearchDto(int review_no, String review_img, String content, int rating, int product_code,
			int member_no, Date created_date) {
		super();
		this.review_no = review_no;
		this.review_img = review_img;
		this.content = content;
		this.rating = rating;
		this.product_code = product_code;
		this.member_no = member_no;
		this.created_date = created_date;
	}


	
	
	
	

}
