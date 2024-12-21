package com.boot.selftop_web.member.customer.controller;

import com.boot.selftop_web.member.customer.biz.CustomerBiz;
import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CustomerController {

	@Autowired
	private CustomerBiz customerBiz;

	@GetMapping("/main")
	public String SelfTopMain() {
		return "mainPage";
	}
	
	@GetMapping("/signup")
	public String showSignUpForm() {
		return "customerSignUp";
	}

	@GetMapping("/mypage")
	public String showCustomerMyPage() {
		return "customerMyPage";
	}
	
	@GetMapping("/infochange")
	public String showInfoChangeForm() {
		return "customerInfoChange";
	}

	@ResponseBody // JSON 응답을 반환
	@GetMapping("/idchk") //ID 중복체크
	public boolean idchk(@RequestParam("id") String id) {
		return customerBiz.idchk(id); // boolean 값을 직접 반환
	}

	@PostMapping("/customerreg")
	public String customerReg(HttpServletRequest request) {
		CustomerDto customerDto = new CustomerDto();

		customerDto.setId(request.getParameter("id"));
		customerDto.setPw(request.getParameter("pw"));
		customerDto.setName(request.getParameter("name"));
		customerDto.setEmail(request.getParameter("email-id") + "@" + request.getParameter("email-domain"));
		customerDto.setPhone(request.getParameter("phone"));
		int res = customerBiz.insertCustomer(customerDto);

		if(res > 0) {
			return "redirect:/loginform";
		} else {
			return "redirect:/signup";
		}
	}
}
