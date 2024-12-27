package com.boot.selftop_web.member.seller.controller;

import com.boot.selftop_web.member.customer.biz.CustomerBiz;
import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.seller.biz.SellerBiz;
import com.boot.selftop_web.member.seller.biz.SellerBizImpl;
import com.boot.selftop_web.member.seller.biz.mapper.ProductStatusMapper;
import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerStockDto;
import com.boot.selftop_web.order.biz.OrderBoardBiz;
import com.boot.selftop_web.order.model.dto.OrderBoardDto;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import com.boot.selftop_web.product.model.dto.CPUDto;
import com.boot.selftop_web.product.model.dto.CaseDto;
import com.boot.selftop_web.product.model.dto.CoolerDto;
import com.boot.selftop_web.product.model.dto.GPUDto;
import com.boot.selftop_web.product.model.dto.HDDDto;
import com.boot.selftop_web.product.model.dto.MainBoardDto;
import com.boot.selftop_web.product.model.dto.PowerDto;
import com.boot.selftop_web.product.model.dto.RAMDto;
import com.boot.selftop_web.product.model.dto.SSDDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Controller
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private SellerBiz sellerBiz;

	@Autowired
	private CustomerBiz customerBiz;

	@Autowired
	private OrderBoardBiz orderboardbiz;

	@GetMapping("/main")
	public String sellermain(HttpSession session,Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";

		}
		int membernum=(int) session.getAttribute("member_no");
		List<SellerOrderDto> res = sellerBiz.selectList(membernum);
		model.addAttribute("seller",res);
		model.addAttribute("membername",session.getAttribute("name"));
		session.setAttribute("table", "order");
		return "sellermain";
	}

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

		int membernum=(int) session.getAttribute("member_no");

		if((String) session.getAttribute("table") == "order") {
			List<SellerOrderDto> res = sellerBiz.selectSearch(startdate,enddate,keyword,membernum);
			model.addAttribute("seller", res);
			return "sellermain :: tbody";
		}else {
			List<SellerStockDto> res = sellerBiz.selectStocksearch(keyword,membernum);
			model.addAttribute("stocktable", res);
			return "sellerstock :: tbody";
		}
	}

	@GetMapping("/stockmenu")
	public String changesellerorderpage(HttpSession session, Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}
		int membernum=(int) session.getAttribute("member_no");
		List<SellerStockDto> res = sellerBiz.selectStock(membernum);
		model.addAttribute("stocktable",res);
		model.addAttribute("membername",session.getAttribute("name"));
		session.setAttribute("table", "stock");
		return "sellerstock :: changetable";
	}

	@GetMapping("/ordermenu")
	public String loadOrder(HttpSession session, Model model) {
	    if (session.getAttribute("member_no") == null) {
	        return "redirect:/loginform";
	    }
	    int membernum = (int) session.getAttribute("member_no");
	    List<SellerOrderDto> res = sellerBiz.selectList(membernum);
	    model.addAttribute("seller", res);
	    model.addAttribute("membername",session.getAttribute("name"));
	    session.setAttribute("table", "order");
	    return "sellerordertable :: changetable"; // 주문내역 테이블 프래그먼트 반환
	}

	@GetMapping("/infoChange")
	public String showInfoChangeForm(HttpSession session,Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");

		SellerDto sellerInfo = sellerBiz.getSellerInfoByMemberNo(member_no);
	    model.addAttribute("sellerInfo", sellerInfo);

		return "sellerInfoChange";
	}

	@PostMapping("/updatestock")
	public String updatestock(@RequestBody List<Map<String, String>> stockdata,HttpSession session) {
		int membernum = (int) session.getAttribute("member_no");
		for(Map<String,String> data:stockdata) {
			int productcode=Integer.parseInt(data.get("productcode"));
			int price = Integer.parseInt(data.get("price"));
			int amount = Integer.parseInt(data.get("amount"));
			sellerBiz.updatestock(productcode,price,amount,membernum);

		}

		 return "sellerordertable :: changetable";

	}

	@PostMapping("/informorder")
	public ResponseEntity<List<OrderBoardDto>> informorder(@RequestBody int info) {
		int ordernum = info;
		List<OrderBoardDto> res = orderboardbiz.vieworderboard(ordernum);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+res);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/changeaccount")
	public String changeaccount(@RequestParam("phone") String phone,
							    @RequestParam("address1") String address1,
							    @RequestParam("address2") String address2,
							    HttpSession session) {

		// 세션에서 로그인한 사용자 정보 가져오기
	    Integer member_no = (Integer) session.getAttribute("member_no");

	    // 연락처, 주소 변경 처리
	    SellerDto dto = new SellerDto();
	    dto.setMember_no(member_no);  // 세션에서 가져온 member_no 설정

	    String address = address1 + " " + address2;
	    System.out.println(phone);
		System.out.println(address);

	    int resphone = sellerBiz.updatephone(dto, phone);
	    int resaddr = sellerBiz.updateaddr(dto, address);

	    if (resphone > 0 && resaddr > 0) {
	        return "redirect:myPage";
	    } else {
	        return "redirect:infoChange";
	    }
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
	@GetMapping("/idchk") //ID 중복체크
	public boolean idchk(@RequestParam("id") String id) {
		return customerBiz.idchk(id); // boolean 값을 직접 반환
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
		System.out.println("controller customer: " + customerDto);
		System.out.println("controller seller: " + dto);
		int res = sellerBiz.insertSeller(customerDto, dto);
		if (res > 0) {
			return "redirect:/loginform";
		} else {
			return "redirect:signUp";
		}

	}
	
	//판매자가 제품을 등록(수량, 가격, 날짜)
	@Autowired
	private ProductStatusMapper productStatusMapper;

	@PostMapping("/registerProductStatus")
	@ResponseBody
	public ResponseEntity<?> registerProductStatus(@RequestBody ProductStatusDto productStatus, HttpSession session) {
	    try {
	        Integer sellerNo = (Integer) session.getAttribute("member_no");
	        if (sellerNo == null) {
	            return ResponseEntity.badRequest().body(Map.of("message", "판매자 번호가 세션에 존재하지 않습니다."));
	        }
	        productStatus.setSeller_no(sellerNo);
	        productStatus.setReg_date(new Date());

	        boolean isValidProductCode = productMapper.isValidProductCode(productStatus.getProduct_code());
	        if (!isValidProductCode) {
	            return ResponseEntity.badRequest().body(Map.of("message", "유효하지 않은 제품 코드입니다."));
	        }

	        int result = productStatusMapper.insertProductStatus(productStatus);
	        if (result > 0) {
	            return ResponseEntity.ok(Map.of("message", "제품 등록이 성공적으로 완료되었습니다."));
	        } else {
	            return ResponseEntity.badRequest().body(Map.of("message", "제품 등록에 실패했습니다."));
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "서버 내부 오류가 발생했습니다: " + e.getMessage()));
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

	@GetMapping("/ramProducts")
	public ResponseEntity<List<RAMDto>> fetchRamProducts() {
	    try {
	        List<RAMDto> ramProducts = productMapper.findAllRamProducts();
	        return ResponseEntity.ok(ramProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/mainboardProducts")
	public ResponseEntity<List<MainBoardDto>> fetchMainBoardProducts() {
	    try {
	        List<MainBoardDto> mainboardProducts = productMapper.findAllMainBoardProducts();
	        return ResponseEntity.ok(mainboardProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/caseProducts")
	public ResponseEntity<List<CaseDto>> fetchCaseProducts() {
	    try {
	        List<CaseDto> caseProducts = productMapper.findAllCaseProducts();
	        return ResponseEntity.ok(caseProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/gpuProducts")
	public ResponseEntity<List<GPUDto>> fetchGpuProducts() {
	    try {
	        List<GPUDto> gpuProducts = productMapper.findAllGpuProducts();
	        return ResponseEntity.ok(gpuProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/powerProducts")
	public ResponseEntity<List<PowerDto>> fetchPowerProducts() {
	    try {
	        List<PowerDto> powerProducts = productMapper.findAllPowerProducts();
	        return ResponseEntity.ok(powerProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/ssdProducts")
	public ResponseEntity<List<SSDDto>> fetchSsdProducts() {
	    try {
	        List<SSDDto> ssdProducts = productMapper.findAllSsdProducts();
	        return ResponseEntity.ok(ssdProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/hddProducts")
	public ResponseEntity<List<HDDDto>> fetchHddProducts() {
	    try {
	        List<HDDDto> hddProducts = productMapper.findAllHddProducts();
	        return ResponseEntity.ok(hddProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/coolerProducts")
	public ResponseEntity<List<CoolerDto>> fetchCoolerProducts() {
	    try {
	        List<CoolerDto> coolerProducts = productMapper.findAllCoolerProducts();
	        return ResponseEntity.ok(coolerProducts);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	//판매자가 올린 아이템을 수정, 삭제하는 기능(Product_Status table에서 수정, 삭제)
	@PostMapping("/changeproduct")
	public ResponseEntity<?> changeProductStatus(HttpSession session, @RequestBody(required=false) Map<String, Object> payload,
												 @RequestParam("productCode") int productCode, @RequestParam("action") String action) {
	    Integer sellerNo = (Integer) session.getAttribute("member_no");
	    if (sellerNo == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "로그인이 필요합니다."));
	    }

	    // 수정 기능 처리
	    if ("update".equals(action)) {
	        // 수정할 값이 모두 제공되었는지 확인
	    	Integer price = (Integer) payload.get("price");
	        Integer stock = (Integer) payload.get("stock");

	        try {
	            int result = productStatusMapper.updateProductStatus(price, stock, productCode, sellerNo);
	            if (result > 0) {
	                return ResponseEntity.ok(Map.of("message", "제품 재고가 성공적으로 수정되었습니다."));
	            } else {
	                return ResponseEntity.badRequest().body(Map.of("message", "해당 제품 재고를 수정할 수 없습니다."));
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "서버 오류가 발생했습니다: " + e.getMessage()));
	        }
	    }

	    if ("delete".equals(action)) {
	        try {
	            int result = productStatusMapper.deleteProductStatus(productCode, sellerNo);
	            if (result > 0) {
	                return ResponseEntity.ok(Map.of("message", "제품 삭제가 성공적으로 완료되었습니다."));
	            } else {
	                return ResponseEntity.badRequest().body(Map.of("message", "해당 제품을 삭제할 수 없습니다."));
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "서버 오류가 발생했습니다: " + e.getMessage()));
	        }
	    }

	    return ResponseEntity.badRequest().body(Map.of("message", "지원하지 않는 작업입니다."));
	}

}