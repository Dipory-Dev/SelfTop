package com.boot.selftop_web.quote.model.dto;

public class CartDetailDto {
    private int quote_no;
    private int customer_no;
    private int product_code;
    private int seller_no;
    private int amount;

    public CartDetailDto() {}

    public CartDetailDto(int quote_no, int customer_no, int product_code, int seller_no, int amount) {
        this.quote_no = quote_no;
        this.customer_no = customer_no;
        this.product_code = product_code;
        this.seller_no = seller_no;
        this.amount = amount;
    }

    public int getQuote_no() {
        return quote_no;
    }

    public void setQuote_no(int quote_no) {
        this.quote_no = quote_no;
    }

    public int getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(int customer_no) {
        this.customer_no = customer_no;
    }

    public int getProduct_code() {
        return product_code;
    }

    public void setProduct_code(int product_code) {
        this.product_code = product_code;
    }

    public int getSeller_no() {
        return seller_no;
    }

    public void setSeller_no(int seller_no) {
        this.seller_no = seller_no;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartDetailDto{" +
                "quote_no=" + quote_no +
                ", customer_no=" + customer_no +
                ", product_code=" + product_code +
                ", seller_no=" + seller_no +
                ", amount=" + amount +
                '}';
    }
}
