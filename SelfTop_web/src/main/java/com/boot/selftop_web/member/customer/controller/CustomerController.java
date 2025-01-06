package com.boot.selftop_web.member.customer.controller;

import com.boot.selftop_web.member.customer.biz.KakaoService;
import com.boot.selftop_web.product.biz.ProductInfoBizImpl;
import com.boot.selftop_web.product.model.dto.ProductInfoDto;
import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.CartDetailDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.selftop_web.member.customer.biz.CustomerBiz;
import com.boot.selftop_web.member.customer.biz.CustomerBizImpl;
import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.product.biz.ProductBiz;
import com.boot.selftop_web.product.biz.ProductBizFactory;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import com.boot.selftop_web.product.model.dto.ProductDto;
import com.boot.selftop_web.quote.biz.QuoteBiz;
import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;
import com.boot.selftop_web.member.customer.model.dto.CustomerorderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerStockDto;
import com.boot.selftop_web.order.biz.OrderBoardBiz;
import com.boot.selftop_web.order.biz.OrderDetailBiz;
import com.boot.selftop_web.order.model.dto.OrderBoardDto;
import com.boot.selftop_web.order.model.dto.OrderDetailDto;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/")
public class CustomerController {
	@Autowired
	private CustomerBiz customerBiz;

	@Autowired
	private KakaoService kakaoService;

	@Autowired
	private OrderBoardBiz orderboardBiz;
	
	@Autowired
    private OrderDetailBiz orderDetailBiz;

	@Autowired
	private ProductInfoBizImpl productInfoBiz;

	@Autowired
	private QuoteBiz quoteBiz;
	
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
            			   RedirectAttributes redirectAttributes) {

		// 세션에서 로그인한 사용자 정보 가져오기
	    Integer member_no = (Integer) session.getAttribute("member_no");

		// 비밀번호 변경 처리
	    CustomerDto customerDto = new CustomerDto();
	    customerDto.setMember_no(member_no);  // 세션에서 가져온 member_no 설정

	    String curpw = customerBiz.checkpw(customerDto);
	    if (!curpw.equals(cur_pw)) {
	    	redirectAttributes.addAttribute("message", "기존 비밀번호와 일치하지 않습니다.");
	        return "redirect:infoChange"; // 비밀번호 변경 페이지로 돌아가면서 에러 메시지를 표시
	    }
		int resnewpw = customerBiz.changepw(customerDto, new_pw);
		if (resnewpw > 0) {
			redirectAttributes.addAttribute("message", "비밀번호가 변경되었습니다. 다시 로그인 해주세요.");
			session.invalidate();
			return "redirect:/";
		}
		else {
			redirectAttributes.addAttribute("message", "비밀번호 변경에 실패했습니다.");
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
	public String SelfTopMain(HttpSession session, Model model) {
		Integer member_no = (Integer) session.getAttribute("member_no");
		if (member_no != null) {
			List<CartDTO> cartList = quoteBiz.selectCart(member_no);
			System.out.println(cartList);
			model.addAttribute("cartList", cartList);
		}

		return "mainPage";
	}

	@GetMapping("/quote")
	public ResponseEntity<?> selectCartDetail(@RequestParam("quote_no") int quote_no) {
		System.out.println(quote_no);

		List<CartDetailDto> quote_detail = quoteBiz.selectCartDetail(quote_no);
		for (CartDetailDto cartDetailDto : quote_detail) {
			quoteBiz.selectProduct(cartDetailDto.getProduct_code());
		}

		System.out.println("Quote_detail : " + quote_detail);
		return null;
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

	@GetMapping("/pay")
	public String showPayPage(HttpSession session, Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");

		CustomerDto dto = customerBiz.selectCustomer(member_no);
		model.addAttribute("customer", dto);
		return "customerPay";
	}

	@GetMapping("/payfail")
	public String showPayFail(HttpSession session, Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");

		CustomerDto dto = customerBiz.selectCustomer(member_no);
		model.addAttribute("customer", dto);
		return "payFail";
	}

	@GetMapping("/paysuccess")
	public String showPaySuccess(HttpSession session, Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");

		CustomerDto dto = customerBiz.selectCustomer(member_no);
		model.addAttribute("customer", dto);
		return "paySuccess";
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

	@GetMapping("/order")
	public String customerorder(HttpSession session, Model model) {
		if (session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");
		List<SellerOrderDto> res = customerBiz.selectcustomerorderlist(member_no);
		List<CustomerorderDto> orderres = new ArrayList<>();
//		System.out.println(res.get(0).getAmount());
//		System.out.println(res.size());

		for (SellerOrderDto copyres : res) {
			// 이미 orderres에 해당 order_no가 있는지 확인
			CustomerorderDto exportres = orderres.stream()
					.filter(dto -> dto.getOrder_num() == copyres.getOrder_no()).findFirst().orElse(null);

			if (exportres == null) {
				CustomerorderDto neworder = new CustomerorderDto(copyres.getThumbnail(), copyres.getOrder_Date(),
						copyres.getProduct_name(), copyres.getPrice() * copyres.getAmount(), copyres.getOrder_no(),
						copyres.getOrder_status(), member_no, 0

				);
				orderres.add(neworder);
			} else {
				exportres.setItem(exportres.getItem() + 1);
				exportres.setPrice(exportres.getPrice() + (copyres.getPrice() * copyres.getAmount()));
			}
		}


		model.addAttribute("membername", session.getAttribute("name"));
		model.addAttribute("customerorder",orderres);
		int waitdepositcount = 0; // 입금대기
		int completepaycount = 0; // 결제완료
		int shippingcount = 0; // 결제완료
		int endshippingcount = 0; // 결제완료
		int canclecount = 0; // 결제완료


		// orderres 리스트 순회하면서 주문 상태별 개수 세기
		for (CustomerorderDto order : orderres) {
		    String status = order.getOrder_status();

		    // 주문 상태별로 카운트 증가
		    if ("입금대기".equals(status.replaceAll("\\s+", ""))) {
		    	waitdepositcount++;
		    } else if ("결제완료".equals(status.replaceAll("\\s+", ""))) {
		    	completepaycount++;
		    }else if ("배송중".equals(status.replaceAll("\\s+", ""))) {
		    	shippingcount++;
		    }else if ("배송완료".equals(status.replaceAll("\\s+", ""))) {
		    	endshippingcount++;
		    }else if ("취소".equals(status.replaceAll("\\s+", ""))) {
		    	canclecount++;
		    }else if ("테스트중".equals(status.replaceAll("\\s+", ""))) {
		    	canclecount++;
		    }
		}
		model.addAttribute("waitdepositcount",waitdepositcount);
		model.addAttribute("completepaycount",completepaycount);
		model.addAttribute("shippingcount",shippingcount);
		model.addAttribute("endshippingcount",endshippingcount);
		model.addAttribute("canclecount",canclecount);

		return "customerorder";
	}
	
	// 결제 성공 후 주문 정보를 저장하는 메서드
	@PostMapping("/order/save")
	public String saveOrder(@RequestParam int productCode,
	                        @RequestParam int sellerNo,
	                        @RequestParam int amount,
	                        @RequestParam int orderPrice,
	                        @RequestParam String customerName,  // 결제 후 고객 이름
	                        @RequestParam String customerPhone, // 결제 후 고객 전화번호
	                        @RequestParam String shippingAddress, // 결제 후 배송 주소
	                        @RequestParam String zipCode, // 결제 후 우편번호
	                        @RequestParam String orderId, // 결제 후 주문 번호
	                        HttpSession session,
	                        RedirectAttributes redirectAttributes) {

	    Integer memberNo = (Integer) session.getAttribute("member_no");
	    if (memberNo == null) {
	        redirectAttributes.addFlashAttribute("message", "로그인 후 주문을 진행해주세요.");
	        return "redirect:/loginform";
	    }

	    // 주문 정보를 OrderDetailDto 객체에 담기
	    OrderDetailDto orderDetailDto = new OrderDetailDto();
	    orderDetailDto.setCustomer_no(memberNo);  // 로그인한 사용자 번호
	    orderDetailDto.setProduct_code(productCode);  // 상품 코드
	    orderDetailDto.setSeller_no(sellerNo);  // 판매자 번호
	    orderDetailDto.setAmount(amount);  // 주문 수량
	    orderDetailDto.setOrder_price(orderPrice);  // 주문 금액
	    //orderBoardDto.setCustomer_name(customerName);  // 고객 이름
	    //orderDetailDto.setCustomer_phone(customerPhone);  // 고객 전화번호
	    //orderDetailDto.setShipping_address(shippingAddress);  // 배송 주소
	    //orderDetailDto.setZip_code(zipCode);  // 우편번호
	    //orderDetailDto.setOrder_id(orderId);  // 결제 후 주문 번호

	    // 주문 정보 저장
	    try {
	        orderDetailBiz.saveOrderDetail(orderDetailDto);  // OrderDetailBiz를 통해 저장
	        redirectAttributes.addFlashAttribute("message", "주문이 성공적으로 처리되었습니다.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("message", "주문 처리 중 오류가 발생했습니다.");
	    }

	    return "redirect:/order/confirmation";  // 주문 확인 페이지로 리다이렉트
	}

	@GetMapping("/ordersearch")
	public String searchorder(@RequestParam(required = false) String startdate, @RequestParam(required = false) String enddate,
			Model model, HttpSession session) {
		if (startdate == null || startdate.isEmpty()) {
			startdate = null;
		}
		if (enddate == null || enddate.isEmpty()) {
			enddate = null;
		}

		Integer member_no = (Integer) session.getAttribute("member_no");
		List<CustomerorderDto> orderres = new ArrayList<>();
		List<SellerOrderDto> res = customerBiz.searchcustomerorderlist(startdate,enddate,member_no);

		for (SellerOrderDto copyres : res) {
			// 이미 orderres에 해당 order_no가 있는지 확인
			CustomerorderDto exportres = orderres.stream()
					.filter(dto -> dto.getOrder_num() == copyres.getOrder_no()).findFirst().orElse(null);

			if (exportres == null) {
				CustomerorderDto neworder = new CustomerorderDto(copyres.getThumbnail(), copyres.getOrder_Date(),
						copyres.getProduct_name(), copyres.getPrice() * copyres.getAmount(), copyres.getOrder_no(),
						copyres.getOrder_status(), member_no, 0

				);
				orderres.add(neworder);
			} else {
				exportres.setItem(exportres.getItem() + 1);
				exportres.setPrice(exportres.getPrice() + (copyres.getPrice() * copyres.getAmount()));
			}
		}

		model.addAttribute("customerorder",orderres);
		return "customerorder :: tbody";

	}
	@PostMapping("/orderdetail")
	public String customerorderdetail(HttpSession session, Model model,@RequestParam("order_num") String orderNum,
			@RequestParam("orderprice") String orderprice,
			@RequestParam("orderdate") String orderdate,
			@RequestParam("orderstatus") String orderstatus) {
		if (session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");
		List<SellerOrderDto> res = customerBiz.customerpurchaselist(member_no,Integer.parseInt(orderNum));
		List<OrderBoardDto> customerinfo = orderboardBiz.vieworderboard(Integer.parseInt(orderNum));




		model.addAttribute("membername", session.getAttribute("name"));
		model.addAttribute("orderinfo",res);
		model.addAttribute("ordernum",orderNum);
		model.addAttribute("orderprice",orderprice);
		model.addAttribute("orderstatus",orderstatus);
		model.addAttribute("orderdate",orderdate);
		model.addAttribute("customerinfo", customerinfo.get(0));


		return "customerorderdetail";
	}

	//카카오 로그인 기능이 처리되는 페이지
	@RequestMapping(value = "/loginform/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {

		String reqUrl =
				"https://kauth.kakao.com/oauth/authorize?client_id=66f3a9b8a7fc33ae96bc2f1fbc513320&redirect_uri=http://localhost:8080/kakaoLogin&response_type=code";
		return reqUrl;
	}

	@RequestMapping(value = "/kakaoLogin")
	public String oauthKakao(@RequestParam(value = "code",required = false) String code,
							 HttpSession session, RedirectAttributes redirectAttributes) {

		String access_Token = kakaoService.getAccessToken(code);
		CustomerDto kakaouser = kakaoService.getuserinfo(access_Token);

		if (kakaouser == null) {
			redirectAttributes.addFlashAttribute("message", "카카오 로그인에 실패했습니다.");
			return "redirect:/loginform";
		} else {
			session.setAttribute("role", kakaouser.getRole());
			session.setAttribute("member_no", kakaouser.getMember_no());
			if (kakaouser.getRole() == 'D') {
				redirectAttributes.addFlashAttribute("message", "탈퇴된 계정입니다.");
				return "redirect:/loginform";
			} else if (kakaouser.getRole() == 'C') {
				return "redirect:/";
			} else {
				redirectAttributes.addFlashAttribute("message", "계정 정보를 확인해주세요.");
				return "redirect:/loginform";
			}
		}
	}

	//side-panel에서 부품을 선택하면 해당 카테고리의 필터를 보여주는 기능
	@Autowired
    private ProductMapper productMapper;

	@GetMapping("/api/cpu/attributes")
	public ResponseEntity<Map<String, List<String>>> getCpuAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Socket", productMapper.findAllcpuSocket());
	    attributes.put("DDR", productMapper.findAllcpuDdr());
        attributes.put("Generation", productMapper.findAllcpuGeneration());
	    attributes.put("Spec", productMapper.findAllcpuSpec());
	    attributes.put("Inner_VGA", productMapper.findAllcpuInnerVga());
	    attributes.put("Package_Type", productMapper.findAllcpuPackageType());
	    attributes.put("Cooler_Status", productMapper.findAllcpuCoolerStatus());
	    attributes.put("Core", productMapper.findAllcpuCore());
	    attributes.put("Company", productMapper.findAllcpuCompany());
	    return ResponseEntity.ok(attributes);
	}

	@GetMapping("/api/ram/attributes")
	public ResponseEntity<Map<String, List<String>>> getRamAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("DDR", productMapper.findAllramDdr());
	    attributes.put("Storage", productMapper.findAllramStorage());
        attributes.put("Device", productMapper.findAllramDevice());
	    attributes.put("Heatsync", productMapper.findAllramHeatsync());
	    attributes.put("Company", productMapper.findAllramCompany());
	    return ResponseEntity.ok(attributes);
	}

	@GetMapping("/api/ssd/attributes")
	public ResponseEntity<Map<String, List<String>>> getSsdAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Storage", productMapper.findAllssdStorage());
	    attributes.put("Type", productMapper.findAllssdType());
	    attributes.put("Company", productMapper.findAllssdCompany());
	    return ResponseEntity.ok(attributes);
	}

	@GetMapping("/api/power/attributes")
	public ResponseEntity<Map<String, List<String>>> getPowerAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Supply", productMapper.findAllpowerSupply());
	    attributes.put("Plus80", productMapper.findAllpowerPlus80());
	    attributes.put("Formfactor", productMapper.findAllpowerFormfactor());
	    attributes.put("Company", productMapper.findAllpowerCompany());
	    return ResponseEntity.ok(attributes);
	}

	@GetMapping("/api/cooler/attributes")
	public ResponseEntity<Map<String, List<String>>> getCoolerAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Cooler_Type", productMapper.findAllcoolerCooler_Type());
	    attributes.put("Socket", productMapper.findAllcoolerSocket());
	    attributes.put("Company", productMapper.findAllcoolerCompany());
	    return ResponseEntity.ok(attributes);
	}

	@GetMapping("/api/mainboard/attributes")
	public ResponseEntity<Map<String, List<String>>> getMainBoardAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Socket", productMapper.findAllmainboardSocket());
	    attributes.put("Formfactor", productMapper.findAllmainboardFormfactor());
	    attributes.put("Memory_Slot", productMapper.findAllmainboardMemory_Slot());
	    attributes.put("DDR", productMapper.findAllmainboardDdr());
	    attributes.put("Max_Storage", productMapper.findAllmainboardMax_Storage());
	    attributes.put("Company", productMapper.findAllmainboardCompany());
	    return ResponseEntity.ok(attributes);
	}

	@GetMapping("/api/gpu/attributes")
	public ResponseEntity<Map<String, List<String>>> getGpuAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Series", productMapper.findAllgpuSeries());
	    attributes.put("Storage", productMapper.findAllgpuStorage());
	    attributes.put("Length", productMapper.findAllgpuLength());
	    attributes.put("Company", productMapper.findAllgpuCompany());
	    return ResponseEntity.ok(attributes);
	}

	@GetMapping("/api/hdd/attributes")
	public ResponseEntity<Map<String, List<String>>> getHddAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Device", productMapper.findAllhddDevice());
	    attributes.put("Storage", productMapper.findAllhddStorage());
	    attributes.put("Company", productMapper.findAllhddCompany());
	    return ResponseEntity.ok(attributes);
	}

	@GetMapping("/api/case/attributes")
	public ResponseEntity<Map<String, List<String>>> getCaseAttributes() {
	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Power_Status", productMapper.findAllcasePower_Status());
	    attributes.put("Formfactor", productMapper.findAllcaseFormfactor());
	    attributes.put("Tower_Size", productMapper.findAllcaseTower_Size());
	    attributes.put("VGA_Length", productMapper.findAllcaseVga_Length());
	    attributes.put("Power_Size", productMapper.findAllcasePower_Size());
	    attributes.put("Company", productMapper.findAllcaseCompany());
	    return ResponseEntity.ok(attributes);
	}

	//필터에 선택된 체크박스에 따라 데이터를 넘겨줌
	@PostMapping("/api/products/filter/{category}")
	public ResponseEntity<?> filterProducts(@PathVariable String category, @RequestBody Map<String, List<String>> filters) {
	    System.out.println("Received filters: " + filters);
	    ProductBiz<?> productBiz = productBizFactory.getBiz(category);
	    if (productBiz == null) {
	        return ResponseEntity.badRequest().body("Invalid category: " + category);
	    }

	    try {
	        List<?> filteredProducts = productBiz.filterProducts(filters);
	        if (filteredProducts.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(filteredProducts);
	    } catch (Exception e) {
	        System.err.println("Server side error during filtering: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
	    }
	}

	// side-panel에서 부품을 선택하면 카테고리로 설정해 content-box안에 부품들을 리스트해서 보여줌
	@Autowired
	private ProductBizFactory productBizFactory;

	@GetMapping("/products/{category}")
	public ResponseEntity<?> getProductsByCategory(
			@PathVariable String category,
			@RequestParam(value = "sort", defaultValue = "byname") String sort) {

	    System.out.println("Fetching products for category: " + category);
		System.out.println("Sort by : " + sort);


	    ProductBiz<?> productBiz = productBizFactory.getBiz(category.toLowerCase());
	    if (productBiz == null) {
	        System.err.println("No ProductBiz found for category: " + category);
	        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid category: " + category));
	    }


	    List<?> products = productBiz.getProductsByCategory(category, sort);
	    if (products.isEmpty()) {
	        System.out.println("No products found for category: " + category);
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(products);
	}

	@GetMapping("/cartpage")
	public String cartpage(HttpSession session, Model model) {
		if(session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		model.addAttribute("membername", session.getAttribute("name"));
		Integer member_no = (Integer) session.getAttribute("member_no");
		List<QuoteDto> res =quoteBiz.SelectQuote(member_no); //견적리스트
		List<QuoteDetailDto> dummycategory = Arrays.asList(
			    new QuoteDetailDto(0, 0, 0, 0, 0, "CPU", "-", "-", 0),
			    new QuoteDetailDto(0, 0, 0, 0, 0, "RAM", "-", "-", 0),
			    new QuoteDetailDto(0, 0, 0, 0, 0, "그래픽카드", "-", "-", 0),
			    new QuoteDetailDto(0, 0, 0, 0, 0, "메인보드", "-", "-", 0),
			    new QuoteDetailDto(0, 0, 0, 0, 0, "파워", "-", "-", 0),
			    new QuoteDetailDto(0, 0, 0, 0, 0, "HDD", "-", "-", 0),
			    new QuoteDetailDto(0, 0, 0, 0, 0, "SSD", "-", "-", 0),
			    new QuoteDetailDto(0, 0, 0, 0, 0, "쿨러", "-", "-", 0),
			    new QuoteDetailDto(0, 0, 0, 0, 0, "케이스", "-", "-", 0)
			);

			model.addAttribute("quotedetail", dummycategory);

		model.addAttribute("quote", res);

		return "customercart";
	}


	@GetMapping("/quotedetail")
	@ResponseBody
	public List<QuoteDetailDto> cartpagedetail(@RequestParam("quote_no") int quoteNo) {
		List<QuoteDetailDto> selectres=quoteBiz.QuoteDetailinfo(quoteNo);


		return selectres;
	}

	@GetMapping("/productDetail")
	public String productDetail(Model model, @RequestParam("product_code") int product_code) {
		ProductInfoDto dto = productInfoBiz.selectOne(product_code);
		model.addAttribute("product", dto);

		return "popup_product_info";
	}

}
