package com.boot.selftop_web.member.customer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class CustomerController {
	@Autowired
	private CustomerBiz customerBiz;

	@GetMapping("/loginform")
    public String LoginSection(HttpSession session) {
		if(session.getAttribute("member_no") != null) {
			return "redirect:/";
		}
        return "loginform";
    }

	@PostMapping("/loginchk")
	public String loginChk(@RequestParam String id, @RequestParam String pw, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		CustomerDto member = customerBiz.memberlogin(id, pw);
		if(member != null) {
			if (member.getRole() == 'D') {
				// 탈퇴된 계정 메시지와 함께 리다이렉트
				redirectAttributes.addFlashAttribute("message", "탈퇴된 계정입니다.");
				return "redirect:/loginform";
			}

			// 탈퇴된 계정이 아니라면 세션에 정보 설정
			session.setAttribute("role", member.getRole());
			session.setAttribute("member_no", member.getMember_no());
			session.setAttribute("name", member.getName());
			if (member.getRole() == 'S') {
				return "redirect:/seller/main";
			} else if (member.getRole() == 'C') {
				return "redirect:/";
			} else {
				redirectAttributes.addFlashAttribute("message", "계정 정보를 확인해주세요.");
				return "redirect:/loginform";
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "계정 정보를 확인해주세요.");
			return "redirect:/loginform";
		}
    }
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
	
	@GetMapping("/member_no")
	@ResponseBody
	public String getMemberNo(HttpSession session) {
	    Object memberNo = session.getAttribute("member_no");
	    return memberNo != null ? memberNo.toString() : ""; // 회원 번호 반환, 없으면 빈 문자열
	}

	@PostMapping("/customerInfoChange")
	public String customerInfoChange(HttpSession session,
									 @RequestParam("email-id") String email1,
									 @RequestParam("email-domain") String email2,
									 @RequestParam("phone") String phone,
									 RedirectAttributes redirectAttributes) {
		String email = email1 + "@" + email2;
		Integer member_no = (Integer) session.getAttribute("member_no");
		System.out.println(email + " " + member_no + " " + phone);
		int res = customerBiz.changeInfo(email, phone, member_no);
		if (res != 0) {
			redirectAttributes.addAttribute("message", "계정 정보가 변경되었습니다.");
		} else {
			redirectAttributes.addAttribute("message", "계정 정보 변경에 실패했습니다.");
		}
		return "redirect:mypage";
	}
	
	@PostMapping("/changepw")
	public String changepw(@RequestParam("cur_pw") String cur_pw,
            			   @RequestParam("new_pw") String new_pw,
            			   HttpSession session,
            			   Model model,
            			   RedirectAttributes redirectAttributes) {

		// 세션에서 로그인한 사용자 정보 가져오기
	    Integer member_no = (Integer) session.getAttribute("member_no");

		// 비밀번호 변경 처리
	    CustomerDto customerDto = new CustomerDto();
	    customerDto.setMember_no(member_no);  // 세션에서 가져온 member_no 설정

	    String curpw = customerBiz.checkpw(customerDto);
	    if (!curpw.equals(cur_pw)) {
	        model.addAttribute("errorMessage", "기존 비밀번호가 일치하지 않습니다."); // 에러 메시지 전달
	        return "redirect:infoChange"; // 비밀번호 변경 페이지로 돌아가면서 에러 메시지를 표시
	    }
		int resnewpw = customerBiz.changepw(customerDto, new_pw);
		if (resnewpw > 0) {
			redirectAttributes.addAttribute("message", "비밀번호가 변경되었습니다. 다시 로그인 해주세요.");
			session.invalidate();
			return "redirect:/";
		}
		else {
			return "redirect:infoChange";
		}
	}

	@PostMapping("/delUser")
	public String delUser(HttpSession session, @RequestParam("role") String role, @RequestParam("pwOrigin") String pwOrigin, @RequestParam("emailOrigin") String emailOrigin, @RequestParam("id") String id, @RequestParam("email") String email, @RequestParam("pw") String pw, RedirectAttributes redirectAttributes) {
		System.out.println(id + email + pw);
		if (pw.equals(pwOrigin) && email.equals(emailOrigin)) {
			int res = customerBiz.delUser(id, email, pw);
			if (res > 0) {
				redirectAttributes.addAttribute("message", "탈퇴되었습니다.");
				session.invalidate();
				return "redirect:/";
			} else {
				redirectAttributes.addFlashAttribute("message", "오류가 발생하였습니다.");
				return "redirect:/loginform";
			}
		}else {
			redirectAttributes.addFlashAttribute("message", "암호 또는 이메일이 일치하지 않습니다.");
			if (role.equals("S")) {
				return "redirect:/seller/infoChange";
			} else {
				return "redirect:/myPage";
			}
		}
	}

	@GetMapping("/main")
	public String SelfTopMain() {
		return "mainPage";
	}

	@GetMapping("/signup")
	public String showSignUpForm() {
		return "customerSignUp";
	}

	@GetMapping("/mypage")
	public String showCustomerMyPage(HttpSession session, Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");

		CustomerDto dto = customerBiz.selectCustomer(member_no);
		model.addAttribute("customer", dto);
		return "customerMyPage";
	}

	@PostMapping("/infochange")
	public String showInfoChangeForm(HttpSession session, Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");

		CustomerDto dto = customerBiz.selectCustomer(member_no);
		model.addAttribute("customer", dto);
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
