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
	@Select("SELECT REVIEW_NO, REVIEW_IMG,CONTENT, RATING, PRODUCT_CODE, MEMBER_NO, CREATED_DATE FROM REVIEW")
    List<ReviewDto> getAllReviews();
	
	//특정 상품에 대한 리뷰 가져오기
	@Select("""
		    SELECT 
		        r."REVIEW_NO"    AS reviewNo,
		        r."REVIEW_IMG"   AS reviewImg,
		        r."CONTENT"      AS content,
		        r."RATING"       AS rating,
		        r."CREATED_DATE"  AS reviewDate,
		        r."PRODUCT_CODE" AS productCode,
		        r."MEMBER_NO"    AS memberNo,
		        c."NAME"         AS memberName
		    FROM REVIEW r
		    LEFT JOIN CUSTOMER c ON r."MEMBER_NO" = c."MEMBER_NO"
		    WHERE r."PRODUCT_CODE" = #{productCode}
		""")
		List<ReviewDto> getReviewsByProductCode(@Param("productCode") int productCode);
    
	//리뷰 삽입하기
    @Insert("INSERT INTO REVIEW (REVIEW_NO, REVIEW_IMG,CONTENT, RATING, PRODUCT_CODE, MEMBER_NO) " +
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
