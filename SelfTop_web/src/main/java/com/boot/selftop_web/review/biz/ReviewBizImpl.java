package com.boot.selftop_web.review.biz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boot.selftop_web.review.biz.mapper.ReviewMapper;
import com.boot.selftop_web.review.model.dto.ReviewDto;

@Service
public class ReviewBizImpl implements ReviewBiz {

    private final ReviewMapper reviewMapper;

    public ReviewBizImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewMapper.getAllReviews();
    }

    @Override
    public List<ReviewDto> getReviewsByProductCode(int productCode) {
        return reviewMapper.getReviewsByProductCode(productCode);
    }

	@Override
	public boolean addReview(ReviewDto review) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReview(ReviewDto review) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteReview(Long reviewNo) {
		// TODO Auto-generated method stub
		return false;
	}
}