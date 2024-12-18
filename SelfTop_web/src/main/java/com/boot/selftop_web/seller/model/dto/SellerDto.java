package com.boot.selftop_web.seller.model.dto;

public class SellerDto {
<<<<<<< HEAD
    private int member_no;
    private String id;
    private String pw;
    private String name;
    private String email;
    private String phone;
    private String role;

    private String company_name;
    private String ceo_name;
    private String business_license;
    private String address;

    public SellerDto() {}

    public SellerDto(int member_no, String id, String pw, String name, String email, String phone, String role, String company_name, String ceo_name, String business_license, String address) {
        this.member_no = member_no;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.company_name = company_name;
        this.ceo_name = ceo_name;
        this.business_license = business_license;
        this.address = address;
=======
    private Date order_Date;
    private String category;
    private String thumbnail;
    private String product_name;
    private int amount;
    private int price;
    private String order_status;
    private String customer_no;
    
    public SellerDto() {
>>>>>>> d879f07 ([MainPage, sellerMyPage 수정])
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCeo_name() {
        return ceo_name;
    }

    public void setCeo_name(String ceo_name) {
        this.ceo_name = ceo_name;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}