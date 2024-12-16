package com.boot.selftop_web.seller.model.dto;

import java.util.Date;

public class SellerDto {
    private Date order_Date;
    private String category;
    private String thumbnail;
    private String product_name;
    private int amount;
    private int price;
    private String order_status;
    private String customer_no;

    public SellerDto() {
    }

    public SellerDto(Date order_Date, String category, String thumbnail, String product_name, int amount, int price, String order_status, String customer_no) {
        this.order_Date = order_Date;
        this.category = category;
        this.thumbnail = thumbnail;
        this.product_name = product_name;
        this.amount = amount;
        this.price = price;
        this.order_status = order_status;
        this.customer_no = customer_no;
    }

    public Date getOrder_Date() {
        return order_Date;
    }

    public void setOrder_Date(Date order_Date) {
        this.order_Date = order_Date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    @Override
    public String toString() {
        return "SellerDto{" +
                "order_Date=" + order_Date +
                ", category='" + category + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", product_name='" + product_name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", order_status='" + order_status + '\'' +
                ", customer_no='" + customer_no + '\'' +
                '}';
    }
}
