package com.boot.selftop_web.review.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.boot.selftop_web.review.model.dto.ReviewDto;

@Mapper
public interface ReviewMapper {
	
	//모든 리뷰 가져오기
	@Select("SELECT REVIEW_NO, REVIEW_IMG, VIDEO, CONTENT, RATING, PRODUCT_CODE, MEMBER_NO FROM REVIEW")
    List<ReviewDto> getAllReviews();
	
	//특정 상품에 대한 리뷰 가져오기
	@Select("SELECT REVIEW_NO, REVIEW_IMG, CONTENT, RATING, PRODUCT_CODE, MEMBER_NO " +
	        "FROM REVIEW WHERE PRODUCT_CODE = #{productCode}")
	List<ReviewDto> getReviewsByProductCode(@Param("productCode") int productCode);
    
	//리뷰 삽입하기
    @Insert("INSERT INTO REVIEW (REVIEW_NO, REVIEW_IMG, VIDEO, CONTENT, RATING, PRODUCT_CODE, MEMBER_NO) " +
            "VALUES (#{reviewNo}, #{reviewImg}, #{video}, #{content}, #{rating}, #{productCode}, #{memberNo})")
    void insertReview(ReviewDto review);
    
    //리뷰 수정하기
    @Update("UPDATE REVIEW SET CONTENT = #{content}, RATING = #{rating}, REVIEW_IMG = #{reviewImg}, VIDEO =#{video} " +
            "WHERE REVIEW_NO = #{reviewNo}")
    void updateReview(ReviewDto review);

    //리뷰 삭제하기
    @Delete("DELETE FROM REVIEW WHERE REVIEW_NO = #{reviewNo}")
    void deleteReview(@Param("reviewNo") Long reviewNo);
    
    
}
