package com.boot.selftop_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.selftop_web.member.model.biz.MemberBiz;
import com.boot.selftop_web.member.model.dto.MemberDto;
import com.boot.selftop_web.seller.model.dto.AdminDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {	
	@Autowired
	private MemberBiz memberbiz;
	@GetMapping("/loginform")
    public String LoginSection(HttpSession session) {
		if(session.getAttribute("memberno") != null) {
			return "redirect:/intropage.html";		
		}
        return "loginform";
    }

	@PostMapping("/loginchk")
	public String loginChk(@RequestParam String id, @RequestParam String pw, HttpSession session, Model model) {
		MemberDto member = memberbiz.memberlogin(id, pw);
		
		if((member !=null) && member.getRole() == 'S' ) {
			System.out.println("This is seller!");
			 session.setAttribute("memberno", member.getNumber());
			 session.setAttribute("name",member.getName());
			return "redirect:/seller/main";
		}else if((member !=null) && member.getRole() == 'C') {
			System.out.println("Customer");
			return "redirect:/main";
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

}
