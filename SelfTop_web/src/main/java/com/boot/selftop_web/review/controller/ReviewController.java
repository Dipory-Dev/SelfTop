package com.boot.selftop_web.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.selftop_web.review.biz.ReviewBiz;
import com.boot.selftop_web.review.model.dto.ReviewDto;

@Controller
@RequestMapping("/product")
public class ReviewController {

	@Autowired
    private ReviewBiz reviewBiz;

    public ReviewController(ReviewBiz reviewBiz) {
        this.reviewBiz = reviewBiz;
    }

    // 특정 상품의 리뷰 가져오기
    @GetMapping("/reviews")
    public ResponseEntity<?> getProductReviews(@RequestParam("productCode") String productCode) {
    	
    	int product_code = Integer.parseInt( productCode);
    	/*수정중!
        if (productCode == null || productCode <= 0) {
            return ResponseEntity.badRequest().body("Invalid product code: " + productCode);
        }

        try {
            List<ReviewDto> reviews = reviewBiz.getReviewsByProductCode(productCode);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            // 서버 오류 로그 출력
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 데이터를 가져오는 데 실패했습니다.");
        }
        */
        return null;
    }
    
    
    
    // 리뷰 추가
    @PostMapping("/review")
    public String addReview(@ModelAttribute ReviewDto review) {
        reviewBiz.addReview(review);
        return "redirect:/product/reviews?productCode=" + review.getProductCode();
    }

    // 리뷰 수정
    @PutMapping("/review")
    public String updateReview(@ModelAttribute ReviewDto review) {
        reviewBiz.updateReview(review);
        return "redirect:/product/reviews?productCode=" + review.getProductCode();
    }

    // 리뷰 삭제
    @DeleteMapping("/review/{reviewNo}")
    public String deleteReview(@PathVariable Long reviewNo, @RequestParam("productCode") String productCode) {
        reviewBiz.deleteReview(reviewNo);
        return "redirect:/product/reviews?productCode=" + productCode;
    }
}
