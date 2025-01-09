package com.boot.selftop_web.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.selftop_web.product.biz.ProductInfoBizImpl;
import com.boot.selftop_web.review.biz.ReviewBiz;
import com.boot.selftop_web.review.model.dto.ReviewDto;

@Controller
@RequestMapping("/product")
public class ReviewController {

	@Autowired
    private ReviewBiz reviewBiz;
	@Autowired
	private final ProductInfoBizImpl productInfoBizImpl;

	
    public ReviewController(ReviewBiz reviewBiz, ProductInfoBizImpl productInfoBizImpl) {
        this.reviewBiz = reviewBiz;
        this.productInfoBizImpl= productInfoBizImpl;
    }

    // 특정 상품의 리뷰 가져오기
    @GetMapping("/reviews")
    @ResponseBody
    public ResponseEntity<?> getProductReviews(@RequestParam("productCode") Integer productCode) {
    	if (productCode == null || productCode <= 0) {
            return ResponseEntity.badRequest().body("Invalid product code: " + productCode);
        }

        try {
            List<ReviewDto> reviews = reviewBiz.getReviewsByProductCode(productCode);
            
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("리뷰 데이터를 가져오는 데 실패했습니다.");
        }
    }
    
    
}
