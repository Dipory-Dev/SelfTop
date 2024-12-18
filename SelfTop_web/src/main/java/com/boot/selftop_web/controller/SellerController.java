package com.boot.selftop_web.controller;

import com.boot.selftop_web.seller.model.biz.SellerBiz;
import com.boot.selftop_web.seller.model.biz.SellerBizImpl;
import com.boot.selftop_web.seller.model.dto.SellerDto;
import com.boot.selftop_web.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.seller.model.dto.SellerStockDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private SellerBizImpl sellerbiz;
	
	@Autowired
	private SellerBiz sellerBiz;

	@GetMapping("/sellerSignUp")
	public String showSignUpForm() {
		return "sellerSignUp";
	}


	@GetMapping("/sellerMyPage")
	public String showSellerMyPage(HttpSession session,Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/login/loginform";
		}
		
		Integer member_no = (Integer) session.getAttribute("member_no");
		
		SellerDto sellerInfo = sellerBiz.getSellerInfoByMemberNo(member_no);
	    model.addAttribute("sellerInfo", sellerInfo);
		
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
		model.addAttribute("membername",session.getAttribute("name"));
		session.setAttribute("table", "order");
		return "sellermain";
	}

	@GetMapping("/datesearch")
	public String searchByDate(@RequestParam(required = false) String startdate, @RequestParam(required = false) String enddate,
							   @RequestParam(required = false) String keyword,  Model model,HttpSession session) {
		if (startdate == null || startdate.isEmpty()) {
			startdate = null;
		}
		if (enddate == null || enddate.isEmpty()) {
			enddate = null;
		}
		if (keyword == null || keyword.isEmpty()) {
			keyword = null;
		}

		int membernum=(int) session.getAttribute("memberno");

		if((String) session.getAttribute("table") == "order") {
			List<SellerOrderDto> res = sellerbiz.selectSearch(startdate,enddate,keyword,membernum);
			model.addAttribute("seller", res);
			return "sellermain :: tbody";
		}else {
			List<SellerStockDto> res = sellerbiz.selectStocksearch(keyword,membernum);
			model.addAttribute("stocktable", res);
			return "sellerstock :: tbody";
		}
	}
	
	@GetMapping("/stockmenu")
	public String changesellerorderpage(HttpSession session, Model model) {
		if(session.getAttribute("memberno") == null) {
			return "redirect:/login/loginform";
		}
		int membernum=(int) session.getAttribute("memberno");
		List<SellerStockDto> res = sellerbiz.selectStock(membernum);
		model.addAttribute("stocktable",res);
		model.addAttribute("membername",session.getAttribute("name"));
		session.setAttribute("table", "stock");
		return "sellerstock :: changetable";
	}

	@GetMapping("/ordermenu")
	public String loadOrder(HttpSession session, Model model) {
	    if (session.getAttribute("memberno") == null) {
	        return "redirect:/login/loginform";
	    }
	    int membernum = (int) session.getAttribute("memberno");
	    List<SellerOrderDto> res = sellerbiz.selectList(membernum);
	    model.addAttribute("seller", res);
	    model.addAttribute("membername",session.getAttribute("name"));
	    session.setAttribute("table", "order");
	    return "sellerordertable :: changetable"; // 주문내역 테이블 프래그먼트 반환
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

		// 비밀번호 확인
		if (!pw.equals(confirmPassword)) {
			model.addAttribute("error", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return "sellerSignUp";
		}


		// 약관 동의 확인
		if (!terms) {
			model.addAttribute("error", "서비스 약관과 개인정보 처리방침에 동의하셔야 합니다.");
			return "sellerSignUp";
		}

		model.addAttribute("message", "회원가입이 완료되었습니다.");
		return "sellerMain";
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

	@GetMapping("/idchk")
	public void idchk (@RequestParam("id") String id) {
		System.out.println(id);
		System.out.println("controller: " + sellerbiz.idchk(id));
		sellerbiz.idchk(id);
	}


}