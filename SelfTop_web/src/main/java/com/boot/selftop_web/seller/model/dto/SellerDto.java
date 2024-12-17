package com.boot.selftop_web.seller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SellerDto {
    private Date order_Date;       
    private String category;      
    private String thumbnail;   
    private String product_name;
    private int amount;          
    private int price;         
    private String order_status;        
    private String customer_no;
}
