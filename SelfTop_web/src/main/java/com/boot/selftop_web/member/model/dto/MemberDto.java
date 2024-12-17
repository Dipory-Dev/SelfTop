package com.boot.selftop_web.member.model.dto;

public class MemberDto {
	private int number;
	private String id;
	private String pw;
	private String name;
	private String email;
	private String phone;
	private char role;
	
	public class MeberDto{};
	
	
	public MemberDto(int number, String id, String pw, String name, String email, String phone, char role) {
		super();
		this.number = number;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	
	

}
