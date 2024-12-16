package com.boot.selftop_web.seller.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoolerDto {
    private int product_code;
    private String category;
    private String product_name;
    private String company;
    private Date upload_date;
    private String thumbnail;
    private String content_img;
    private String etc;

    private String cooler_type;
    private String socket;
    private int watt;
}