package com.boot.selftop_web.seller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SellerDto {
    private Date order_Date;       
    private String cartegory;      
    private String p_Image;   
    private String p_model;
    private int amount;          
    private int price;         
    private String status;        
    private String c_Inform;
}
