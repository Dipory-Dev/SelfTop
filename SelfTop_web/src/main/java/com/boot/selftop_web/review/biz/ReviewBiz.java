package com.boot.selftop_web.review.biz;

import java.util.List;

import com.boot.selftop_web.review.model.dto.ReviewDto;

public interface ReviewBiz {
	
	// 모든 리뷰 가져오기
    List<ReviewDto> getAllReviews();

    // 특정 상품의 리뷰 가져오기
    List<ReviewDto> getReviewsByProductCode(int productCode);

    // 리뷰 추가
    boolean addReview(ReviewDto review);

    // 리뷰 수정
    boolean updateReview(ReviewDto review);

    // 리뷰 삭제
    boolean deleteReview(Long reviewNo);
}
