package com.boot.selftop_web.member.seller.controller;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.seller.biz.SellerBiz;
import com.boot.selftop_web.member.seller.biz.SellerBizImpl;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerStockDto;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import com.boot.selftop_web.product.model.dto.CPUDto;
import com.boot.selftop_web.product.model.dto.ProductDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	private SellerBiz sellerBiz;

	@GetMapping("/signUp")
	public String showSignUpForm() {
		return "sellerSignUp";
	}

	@GetMapping("/myPage")
	public String showSellerMyPage(HttpSession session,Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");

		SellerDto sellerInfo = sellerBiz.getSellerInfoByMemberNo(member_no);
	    model.addAttribute("sellerInfo", sellerInfo);

		return "sellerMyPage";
	}

	@GetMapping("/main")
	public String sellermain(HttpSession session,Model model) {
		if(session.getAttribute("memberno") == null) {
			return "redirect:/loginform";

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
			return "redirect:/loginform";
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
	        return "redirect:/loginform";
	    }
	    int membernum = (int) session.getAttribute("memberno");
	    List<SellerOrderDto> res = sellerbiz.selectList(membernum);
	    model.addAttribute("seller", res);
	    model.addAttribute("membername",session.getAttribute("name"));
	    session.setAttribute("table", "order");
	    return "sellerordertable :: changetable"; // 주문내역 테이블 프래그먼트 반환
	}

	@GetMapping("/infoChange")
	public String showInfoChangeForm() {

		return "sellerInfoChange";
	}
	
	@PostMapping("/updatestock")
	public String updatestock(@RequestBody List<Map<String, String>> stockdata) {
		System.out.println("-----------------------------------------------------------업데이트 영역진입");
		for(Map<String,String> data:stockdata) {
			int productcode=Integer.parseInt(data.get("productcode"));
			int price = Integer.parseInt(data.get("price"));
			int amount = Integer.parseInt(data.get("amount"));
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + productcode + "," + price + "," + amount);
			sellerbiz.updatestock(productcode,price,amount);
					
		}
		
		 return "sellerordertable :: changetable";
		
	}

//	@PostMapping("/signUp")
//	public String registerSeller(
//			@RequestParam("id") String id,
//			@RequestParam("pw") String pw,
//			@RequestParam("confirmPassword") String confirmPassword,
//			@RequestParam("email") String email,
//			@RequestParam("terms") boolean terms,
//			Model model) {
//
//		// 비밀번호 확인
//		if (!pw.equals(confirmPassword)) {
//			model.addAttribute("error", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
//			return "sellerSignUp";
//		}
//
//
//		// 약관 동의 확인
//		if (!terms) {
//			model.addAttribute("error", "서비스 약관과 개인정보 처리방침에 동의하셔야 합니다.");
//			return "sellerSignUp";
//		}
//
//		model.addAttribute("message", "회원가입이 완료되었습니다.");
//		return "sellerMain";
//	}


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
	@GetMapping("/idchk") //ID 중복체크
	public boolean idchk(@RequestParam("id") String id) {
		System.out.println("controller : " + id);
		return sellerbiz.idchk(id); // boolean 값을 직접 반환
	}

	@PostMapping("/regist")
	public String sellerReg(HttpServletRequest request) {
		CustomerDto customerDto = new CustomerDto();
		SellerDto dto = new SellerDto();
		customerDto.setId(request.getParameter("id"));
		customerDto.setPw(request.getParameter("pw"));
		customerDto.setName(request.getParameter("name"));
		customerDto.setEmail(request.getParameter("email-id") + "@" + request.getParameter("email-domain"));
		customerDto.setPhone(request.getParameter("phone"));
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
		int res = sellerbiz.insertSeller(customerDto, dto);
		if (res > 0) {
			return "redirect:/loginform";
		} else {
			return "redirect:signUp";
		}

	}
	
	//제품 등록할때 CPU선택하면 모든 CPU의 코드, 이름, 설명 가져오기
	@Autowired
    private ProductMapper productMapper;

	@GetMapping("/cpuProducts")
	public ResponseEntity<List<CPUDto>> fetchCpuProducts() {
	    try {
	        List<CPUDto> cpuProducts = productMapper.findAllCpuProducts();
	        return ResponseEntity.ok(cpuProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

}