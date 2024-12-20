package com.boot.selftop_web.member.customer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.selftop_web.member.customer.biz.CustomerBiz;
import com.boot.selftop_web.member.customer.model.dto.CustomerDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {	
	@Autowired
	private CustomerBiz customerBiz;

	@GetMapping("/loginform")
    public String LoginSection(HttpSession session) {
		if(session.getAttribute("memberno") != null) {
			return "redirect:/intropage.html";		
		}
        return "loginform";
    }

	@PostMapping("/loginchk")
	public String loginChk(@RequestParam String id, @RequestParam String pw, HttpSession session, Model model) {
		CustomerDto member = customerBiz.memberlogin(id, pw);
		
		if((member !=null) && member.getRole() == 'S' ) {
			System.out.println("This is seller!");
			 session.setAttribute("memberno", member.getMember_no());
			 session.setAttribute("member_no", member.getMember_no());
			 session.setAttribute("name",member.getName());
			return "redirect:/intropage.html";
		}else if((member !=null) && member.getRole() == 'C') {
			session.setAttribute("member_no", member.getMember_no());
			System.out.println("Customer");
			return "redirect:/seller/main";
		}else if((member !=null) && member.getRole() == 'D') {
			session.setAttribute("member_no", member.getMember_no());
			System.out.println("Customer");
			return "redirect:/seller/main";
		}else {
            return "loginform";
        }
			
	
	}
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/intropage.html";
    }
	
	@GetMapping("/memberno")
	@ResponseBody
	public String getMemberNo(HttpSession session) {
	    Object memberNo = session.getAttribute("memberno");
	    return memberNo != null ? memberNo.toString() : ""; // 회원 번호 반환, 없으면 빈 문자열
	}
	
	@GetMapping("/verifyPW") //기존 비밀번호 확인
	@ResponseBody
	public boolean verfiyPW(@RequestParam("pw") String pw) {
		return customerBiz.verifyPW(pw); // boolean 값을 직접 반환
	}

	@PostMapping("/delUser")
	public String delUser(@RequestParam("email") String email, @RequestParam("pw") String pw) {
		System.out.println(email);
		System.out.println(pw);
		int res = customerBiz.delUser(email, pw);
		if (res > 0) {
			return "/loginform";
		}
		else {
			return "redirect:/intropage.html";
		}
	}

}
