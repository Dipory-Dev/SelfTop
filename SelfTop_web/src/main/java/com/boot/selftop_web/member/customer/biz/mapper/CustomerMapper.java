package com.boot.selftop_web.member.customer.biz.mapper;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.customer.model.dto.CustomerorderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerMapper {
    @Insert("INSERT INTO customer VALUES (seq_member.nextval, #{id}, #{pw}, #{name}, #{email}, #{phone}, 'C')")
    int insertCustomer(CustomerDto dto);

    @Select("select seq_member.currval from dual")
    int getCurrentMemberNo();

    @Select("<script>" + "SELECT * FROM customer " + "WHERE ID = #{id} " + "AND PW = #{pw}" + "</script>")
    CustomerDto memberlogin(@Param("id") String id, @Param("pw") String pw);

    @Select("SELECT pw FROM customer WHERE member_no = #{dto.member_no}")
    String checkpw(@Param("dto") CustomerDto dto);

    @Update("UPDATE customer SET pw = #{new_pw} WHERE member_no = #{dto.member_no}")
    int changepw(@Param("dto") CustomerDto dto, @Param("new_pw") String new_pw);

    @Update("update customer set role = 'D' where id = #{id} and email = #{email} and pw = #{pw}")
    int delUser(@Param("id") String id, @Param("email") String eamil, @Param("pw") String pw);

    @Select("SELECT * from customer where id = #{id}")
    SellerDto idchk(@org.springframework.data.repository.query.Param("id") String id);

    @Select("select * from customer where member_no = #{member_no}")
    CustomerDto selectCustomer(@Param("member_no")int member_no);

    @Update("update customer set email=#{email}, phone = #{phone} where member_no = #{member_no}")
    int changeInfo(@Param("email") String email, @Param("phone") String phone, @Param("member_no") int member_no);


    @Select("SELECT * FROM customer WHERE id = #{id}")
    CustomerDto findkakao(@Param("id") String id);


    @Select("SELECT * FROM viewsellerorder WHERE customer_no = #{member_no}")
    List<SellerOrderDto> selectcustomerorderlist(@Param("member_no")int member_no);
    
    @Select("SELECT * FROM viewsellerorder WHERE customer_no = #{member_no} AND order_no =#{order_no}")
    List<SellerOrderDto> customerpurchaselist(@Param("member_no")int member_no,@Param("order_no") int order_no);

   @Select("<script>" +
	        "SELECT * FROM viewsellerorder " +
	        "WHERE customer_no= #{member_no} " +
	        "<if test='startdate != null and !startdate.isEmpty()'>" +
	        "   AND order_date &gt;= #{startdate} " +
	        "</if>" +
	        "<if test='enddate != null and !enddate.isEmpty()'>" +
	        "   AND order_date &lt;= #{enddate} " +
	        "</if>" +
	        "</script>")
    List<SellerOrderDto> searchcustomerorderlist(@Param("startdate")String startdate,@Param("enddate") String enddate,@Param("member_no") int member_no);

   @Insert("insert into review values (seq_review.nextval, #{review_img}, #{content}, #{rating}, #{product_code}, #{member_no},current_date)")
    int insertReview(@Param("review_img")String review_img, @Param("content")String content, @Param("rating")int rating, @Param("product_code")int product_code, @Param("member_no")int member_no);

   @Update("update review set review_img=#{filePath},content=#{content},rating=#{rating}  where product_code=#{product_code} and member_no=#{member_no} ")
	int upadteReview(@Param("filePath")String filePath,@Param("content") String content,@Param("rating") int rating,@Param("product_code") int product_code,@Param("member_no") int member_no);
}
