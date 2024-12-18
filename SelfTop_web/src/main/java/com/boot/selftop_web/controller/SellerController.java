package com.boot.selftop_web.controller;

import com.boot.selftop_web.seller.model.biz.SellerBizImpl;
import com.boot.selftop_web.seller.model.dto.SellerDto;
import com.boot.selftop_web.seller.model.dto.SellerOrderDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Controller
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private SellerBizImpl sellerbiz;

	@GetMapping("/sellerSignUp")
	public String showSignUpForm() {
		return "sellerSignUp";
	}


	@GetMapping("/sellerMyPage")
	public String showSellerMyPage(HttpSession session,Model model) {
		if(session.getAttribute("memberno") == null) {
			return "redirect:/login/loginform";
		}
		int membernum=(int) session.getAttribute("memberno");
		List<SellerDto> res = sellerbiz.selectList(membernum);
		model.addAttribute("seller",res);
		return "sellerMyPage";
	}

	@GetMapping("/main")
	public String sellermain(HttpSession session,Model model) {
		if(session.getAttribute("memberno") == null) {
			return "redirect:/login/loginform";
		}
		int membernum=(int) session.getAttribute("memberno");
		List<SellerOrderDto> res = sellerbiz.selectList(membernum);
		model.addAttribute("seller",res);
		return "sellermain";
	}

	@GetMapping("/datesearch")
	public String searchByDate(@RequestParam(required = false) String startdate, @RequestParam(required = false) String enddate,
							   @RequestParam(required = false) String keyword,  Model model) {
		if (startdate == null || startdate.isEmpty()) {
			startdate = null;
		}
		if (enddate == null || enddate.isEmpty()) {
			enddate = null;
		}
		if (keyword == null || keyword.isEmpty()) {
			keyword = null;
		}

		System.out.println(keyword);

		List<SellerOrderDto> res = sellerbiz.selectSearch(startdate,enddate,keyword);
		model.addAttribute("seller", res);

		return "sellermain :: tbody";
	}
	@GetMapping("/sellerInfoChange")
	public String showInfoChangeForm() {
		return "sellerInfoChange";
	}

	@PostMapping("/sellerSignUp")
	public String registerSeller(
			@RequestParam("id") String id,
			@RequestParam("pw") String pw,
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("email") String email,
			@RequestParam("terms") boolean terms,
			Model model) {

		// 필수 항목 확인
		if (id == null || id.isEmpty()) {
			model.addAttribute("error", "아이디를 입력해주세요.");
			return "sellerSignUp";
		}
		if (pw == null || pw.isEmpty()) {
			model.addAttribute("error", "비밀번호를 입력해주세요.");
			return "sellerSignUp";
		}
		if (confirmPassword == null || confirmPassword.isEmpty()) {
			model.addAttribute("error", "비밀번호 확인을 입력해주세요.");
			return "sellerSignUp";
		}
		if (email == null || email.isEmpty()) {
			model.addAttribute("error", "이메일을 입력해주세요.");
			return "sellerSignUp";
		}

		// 비밀번호 확인
		if (!pw.equals(confirmPassword)) {
			model.addAttribute("error", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return "sellerSignUp";
		}

		// 이메일 형식 확인
		if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
			model.addAttribute("error", "유효한 이메일 주소를 입력해주세요.");
			return "sellerSignUp";
		}

		// 약관 동의 확인
		if (!terms) {
			model.addAttribute("error", "서비스 약관과 개인정보 처리방침에 동의하셔야 합니다.");
			return "sellerSignUp";
		}

		model.addAttribute("message", "회원가입이 완료되었습니다.");
		return "redirect:/main";
	}


	@RequestMapping(value="/addressPopup")
	public ModelAndView addressPopup(HttpServletRequest request, @RequestParam HashMap<String, String> p, Locale locale) {

		// callback 함수가 실행되어야하니 호출한 html 파일로 return
		ModelAndView mav = new ModelAndView("addressPopup");

		String inputYn = request.getParameter("inputYn");
		String zipNo = request.getParameter("zipNo");
		String roadAddrPart1 = request.getParameter("roadAddrPart1");
		String roadAddrPart2 = request.getParameter("roadAddrPart2");
		String addrDetail = request.getParameter("addrDetail");
		String jibunAddr = request.getParameter("jibunAddr");


		mav.addObject("inputYn", inputYn);
		mav.addObject("zipNo", zipNo);
		mav.addObject("roadAddrPart1", roadAddrPart1);
		mav.addObject("roadAddrPart2", roadAddrPart2);
		mav.addObject("jibunAddr", jibunAddr);
		mav.addObject("addrDetail", addrDetail);


		return mav;
	}

	@ResponseBody // JSON 응답을 반환
	@GetMapping("/idchk")
	public boolean idchk(@RequestParam("id") String id) {
		return sellerbiz.idchk(id); // boolean 값을 직접 반환
	}

	@PostMapping("/sellerReg")
	public String sellerReg(HttpServletRequest request) {
		SellerDto dto = new SellerDto();
		dto.setId(request.getParameter("id"));
		dto.setPw(request.getParameter("pw"));
		dto.setName(request.getParameter("name"));
		dto.setEmail(request.getParameter("email-id") + "@" + request.getParameter("email-domain"));
		dto.setPhone(request.getParameter("phone"));
		dto.setCompany_name(request.getParameter("company_name"));
		dto.setCeo_name(request.getParameter("ceo_name"));
		dto.setBusiness_license(request.getParameter("business_license"));
		dto.setAddress(request.getParameter("address1") + " " + request.getParameter("address2"));
		System.out.println("controller: " + dto);
		int res = sellerbiz.insertSeller(dto);
		if (res > 0) {
			return "redirect:/login/loginform";
		} else {
			return "redirect:sellerSignUp";
		}

	}

}