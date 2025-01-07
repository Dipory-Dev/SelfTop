package com.boot.selftop_web.review.model.dto;

public class ReviewDto {
	private int reviewNo;
    private String reviewImg;
    private String video;
    private String content;
    private int rating;
    private int productCode;
    private int memberNo;
    private int reviewCount;
    
    public ReviewDto() {}
    
	public ReviewDto(int reviewNo, String reviewImg, String video, String content, int rating, int productCode,
			int memberNo, int reviewCount) {
		super();
		this.setReviewNo(reviewNo);
		this.setReviewImg(reviewImg);
		this.setVideo(video);
		this.setContent(content);
		this.setRating(rating);
		this.setProductCode(productCode);
		this.setMemberNo(memberNo);
		this.setReviewCount(reviewCount);
	}

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getReviewImg() {
		return reviewImg;
	}

	public void setReviewImg(String reviewImg) {
		this.reviewImg = reviewImg;
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

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	
	
	
}
