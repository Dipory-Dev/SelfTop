package com.boot.selftop_web.member.customer.model.dto;

public class CustomerDto {
	private int member_no;
	private String id;
	private String pw;
	private String name;
	private String email;
	private String phone;
	private char role;

	public CustomerDto() {}

	public CustomerDto(int member_no, String id, String pw, String name, String email, String phone, char role) {
		this.member_no = member_no;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
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

	public char getRole() {
		return role;
	}

	public void setRole(char role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "CustomerDto{" +
				"member_no=" + member_no +
				", id='" + id + '\'' +
				", pw='" + pw + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", role=" + role +
				'}';
	}
}
