package com.boot.selftop_web.member.customer.controller;

import com.boot.selftop_web.member.customer.biz.KakaoService;
import com.boot.selftop_web.member.seller.model.dto.ProductStatusDto;
import com.boot.selftop_web.product.biz.ProductInfoBizImpl;
import com.boot.selftop_web.product.model.dto.ProductInfoDto;
import com.boot.selftop_web.quote.model.dto.CartDTO;
import com.boot.selftop_web.quote.model.dto.CartDetailDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.product.biz.ProductBiz;
import com.boot.selftop_web.product.biz.ProductBizFactory;
import com.boot.selftop_web.product.biz.mapper.ProductMapper;
import com.boot.selftop_web.quote.biz.QuoteBiz;
import com.boot.selftop_web.quote.model.dto.QuoteDetailDto;
import com.boot.selftop_web.quote.model.dto.QuoteDto;
import com.boot.selftop_web.quote.model.dto.QuotecomparisonDto;
import com.boot.selftop_web.review.biz.ReviewBiz;
import com.boot.selftop_web.review.model.dto.ReviewDto;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import com.boot.selftop_web.review.model.dto.reviewsearchDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.boot.selftop_web.member.customer.model.dto.CustomerorderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.order.biz.OrderBoardBiz;
import com.boot.selftop_web.order.model.dto.OrderBoardDto;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

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
	private ProductInfoBizImpl productInfoBiz;

	@Autowired
	private QuoteBiz quoteBiz;
	
	@Autowired
	private ReviewBiz reviewBiz;

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
	public String SelfTopMain(HttpSession session, Model model,
	                          @RequestParam(required = false) String category,
	                          @RequestParam(required = false) String search) {
	    Integer member_no = (Integer) session.getAttribute("member_no");
	    String username = (String) session.getAttribute("name");
	    
	    if (member_no != null) {
	        List<CartDTO> cartList = quoteBiz.selectCart(member_no);
	        model.addAttribute("cartList", cartList);
	        model.addAttribute("username", username);
	        CustomerDto dto = customerBiz.selectCustomer(member_no);
	        if (dto == null) {
	            dto = new CustomerDto(); // 기본값 설정
	            dto.setName("Guest");
	        }
	        model.addAttribute("customer", dto);
	    } else {
	        model.addAttribute("customer", null); // 로그인하지 않은 상태
	    }

	    if (category != null && search != null) {
	        ProductBiz<?> productBiz = productBizFactory.getBiz(category.toLowerCase());
	        if (productBiz == null) {
	            System.err.println("No ProductBiz found for category: " + category);
	            model.addAttribute("error", "Invalid category: " + category);
	        } else {
	            List<?> products = productBiz.getProductsByCategory(category, "bypopular", search);
	            model.addAttribute("products", products);
	            model.addAttribute("category", category);
	            model.addAttribute("searchTerm", search);
	        }
	    }

	    return "mainPage";
	}
	
	@GetMapping("/mainPage")
	public String showMainPage(@RequestParam("category") String category, @RequestParam("search") String search, Model model) {
	    ProductBiz<?> productBiz = productBizFactory.getBiz(category);
	    if (productBiz == null) {
	        model.addAttribute("error", "Invalid category: " + category);
	        return "errorPage";
	    }

	    try {
	        List<?> products = productBiz.filterProducts(Collections.emptyMap(), "byname", search);
	        model.addAttribute("products", products);
	        return "mainPage";
	    } catch (Exception e) {
	        model.addAttribute("error", "Error processing request");
	        return "errorPage";
	    }
	}

	@GetMapping("/mainPage")
	public String showMainPage(@RequestParam("category") String category, @RequestParam("search") String search, Model model) {
	    ProductBiz<?> productBiz = productBizFactory.getBiz(category);
	    if (productBiz == null) {
	        model.addAttribute("error", "Invalid category: " + category);
	        return "errorPage";
	    }

	    try {
	        List<?> products = productBiz.filterProducts(Collections.emptyMap(), "byname", search);
	        model.addAttribute("products", products);
	        return "mainPage";
	    } catch (Exception e) {
	        model.addAttribute("error", "Error processing request");
	        return "errorPage";
	    }
	}


	@GetMapping("/quote")
	public ResponseEntity<?> selectCartDetail(@RequestParam("quote_no") int quote_no) {
		System.out.println(quote_no);

		// 견적 상세 정보를 가져옴
		List<CartDetailDto> quote_detail = quoteBiz.selectCartDetail(quote_no);

		// 조립여부 확인
		char assemblyStatus = quoteBiz.assemblecheck(quote_no);

		System.out.println("assemblyStatus: " + assemblyStatus);

		// 결과를 저장할 Map
		Map<String, Object> res = new HashMap<>();

		// 각 제품 정보를 처리
		for (CartDetailDto cartDetailDto : quote_detail) {
			int p_code = cartDetailDto.getProduct_code();
			int amount = cartDetailDto.getAmount();
			System.out.println("amount: " + amount);

			System.out.println("p_code: " + p_code);

			// 제품 정보
			ProductInfoDto productInfo = quoteBiz.selectProduct(p_code);
			System.out.println("ProductInfoDto: " + productInfo);

			// 제품 상태 정보
			ProductStatusDto productStatus = quoteBiz.selectProductStatus(p_code);
			System.out.println("ProductStatusDto: " + productStatus);

			// 결과에 추가
			Map<String, Object> productData = new HashMap<>();
			productData.put("product_name", productInfo.getProduct_name());
			productData.put("price", productStatus.getPrice());
			productData.put("seller_no", productStatus.getSeller_no());
			productData.put("amount", amount);
			productData.put("thumbnail", productInfo.getThumbnail());
			productData.put("product_code", p_code);
			productData.put("assemblyStatus", assemblyStatus);

			res.put(productInfo.getCategory(), productData);
		}

		System.out.println("res : " + res.toString());

		// 결과 반환
		return ResponseEntity.ok(res);
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
						copyres.getProduct_name(), copyres.getProduct_code(), copyres.getPrice() * copyres.getAmount(), copyres.getOrder_no(),
						copyres.getOrder_status(), member_no, 0

				);
				orderres.add(neworder);
				System.out.println("neworder : " + neworder);
			} else {
				exportres.setItem(exportres.getItem() + 1);
				exportres.setPrice(exportres.getPrice() + (copyres.getPrice() * copyres.getAmount()));
			}

		}


		model.addAttribute("membername", session.getAttribute("name"));
		CustomerDto dto = customerBiz.selectCustomer(member_no);
		model.addAttribute("customer", dto);
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
		    if ("취소요청".equals(status.replaceAll("\\s+", ""))) {
		    	waitdepositcount++;
		    } else if ("취소거절".equals(status.replaceAll("\\s+", ""))) {
		    	completepaycount++;
		    }else if ("배송중".equals(status.replaceAll("\\s+", ""))) {
		    	shippingcount++;
		    }else if ("배송완료".equals(status.replaceAll("\\s+", ""))) {
		    	endshippingcount++;
		    }else if ("취소완료".equals(status.replaceAll("\\s+", ""))) {
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
						copyres.getProduct_name(), copyres.getProduct_code(), copyres.getPrice() * copyres.getAmount(), copyres.getOrder_no(),
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

	@PostMapping("/confirmOrderStatus")
    @ResponseBody
    public ResponseEntity<?> confirmOrderStatus(@RequestBody Map<String, Object> payload) {
        int orderNum = Integer.parseInt(payload.get("order_no").toString());
        boolean updated = orderboardBiz.completeOrder(orderNum);
        if (updated) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "상태 업데이트 실패"));
        }
    }

	//orderdetail페이지에서 활용하는 메소드
	@PostMapping("/orderdetail")
	public String customerorderdetail(HttpSession session, Model model,
			@RequestParam("order_num") String orderNum,
			@RequestParam("orderprice") String orderprice,
			@RequestParam("orderdate") String orderdate,
			@RequestParam("orderstatus") String orderstatus,
		    @RequestParam("product_code") String product_code) {
		if (session.getAttribute("member_no") == null) {
			return "redirect:/loginform";
		}

		Integer member_no = (Integer) session.getAttribute("member_no");
		List<SellerOrderDto> res = customerBiz.customerpurchaselist(member_no,Integer.parseInt(orderNum));
		List<OrderBoardDto> customerinfo = orderboardBiz.vieworderboard(Integer.parseInt(orderNum));
		List<reviewsearchDto> reviewsearchres=orderboardBiz.reviewsearchres(member_no);
		//int searchreviewnum=0;
		String deliveryNo = orderboardBiz.getDeliveryNo(Integer.parseInt(orderNum));
		List<Integer> productCodes = reviewsearchres.stream()
			    .map(reviewsearchDto::getProduct_code)  // OrderDTO에서 productCode만 추출
			    .collect(Collectors.toList());

		model.addAttribute("productcodes",productCodes);
		try {
			model.addAttribute("review",new ObjectMapper().writeValueAsString(reviewsearchres));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("membername", session.getAttribute("name"));
		CustomerDto dto = customerBiz.selectCustomer(member_no);
		model.addAttribute("customer", dto);
		model.addAttribute("orderinfo",res);
		model.addAttribute("ordernum",orderNum);
		model.addAttribute("orderprice", NumberFormat.getInstance(Locale.KOREA).format(Integer.parseInt(orderprice)));
		model.addAttribute("allprice", NumberFormat.getInstance(Locale.KOREA).format(Integer.parseInt(orderprice)+ 5000));
		model.addAttribute("orderstatus",res.get(0).getOrder_status());
		model.addAttribute("product_code", product_code);
		model.addAttribute("orderdate",orderdate);
		model.addAttribute("customerinfo", customerinfo.get(0));
		model.addAttribute("deliveryNo", deliveryNo);// 송장번호도 모델에 추가

		return "customerorderdetail";
	}


	@PostMapping("/reviewreg")
	public String reviewReg(Model model, HttpSession session,
							@RequestParam("review_img") MultipartFile review_img,
							@RequestParam("rating") int rating,
							@RequestParam("product_code") int productCode,
							@RequestParam("content") String content,
							@RequestParam("reviewcondition")String reviewcondition){
		// 세션에서 회원 번호 가져오기
		int member_no = (Integer) session.getAttribute("member_no");
		boolean condition = Boolean.parseBoolean(reviewcondition);
		System.out.println("회원 번호: " + member_no);
		int res=0;
		// 현재 프로젝트 경로를 기준으로 저장 경로 설정
		String projectPath = System.getProperty("user.dir");
		String uploadDir = projectPath + "/src/main/resources/review_img"; // 저장 폴더
		String fileName = member_no + review_img.getOriginalFilename(); // 파일명
		String filePath = uploadDir + File.separator + fileName;


		if (review_img.isEmpty()){
			fileName = "no_img.jpg";
		} else {
			try {
				File saveFile = new File(filePath);
				// 디렉토리가 없으면 생성
				if (!saveFile.getParentFile().exists()) {
					saveFile.getParentFile().mkdirs();
				}
				review_img.transferTo(saveFile); // 파일을 저장
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("message", "파일 업로드에 실패했습니다.");
			}
		}

		// DB에 저장할 경로 (/src/main부터)
		String dbPath = "/src/main/resources/review_img/" + fileName;

		// 파일 경로와 리뷰 데이터 로깅
		System.out.println("Review Image Path: " + filePath);
		System.out.println("Rating: " + rating);
		System.out.println("Product Code: " + productCode);
		System.out.println("Review Content: " + content);

		// DB 저장
		if(condition) {
			res =customerBiz.updateReviewimage(filePath, content, rating, productCode, member_no);
		}else {
			res = customerBiz.insertReview(dbPath, content, rating, productCode, member_no);
		}

		if (res > 0) {
			System.out.println("upload success");
		} else {
			System.out.println("upload fail");
		}
		return "redirect:order";
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
		List<String> ddrLs = productMapper.findAllcpuDdr();
		List<String> ddrres = new ArrayList<>();
		for (String ddr : ddrLs) {
			if (ddr != null && ddr.contains(",")) {
				for (String f : ddr.split(",")) {
					if (!ddrres.contains(f.trim())) {
						ddrres.add(f.trim());
					}
				}
			} else {
				if (ddr != null && !ddr.trim().isEmpty()) {
					ddrres.add(ddr.trim());
				} else {
					ddrres.add(ddr);
				}
			}
		}

	    Map<String, List<String>> attributes = new HashMap<>();
	    attributes.put("Socket", productMapper.findAllcpuSocket());
	    attributes.put("DDR", ddrres);
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

		List<String> formfactorLs = productMapper.findAllcaseFormfactor();
		List<String> fres = new ArrayList<>();
		for (String fftor : formfactorLs) {
			if (fftor != null && fftor.contains(",")) {
				for (String f : fftor.split(",")) {
					if (!fres.contains(f.trim())) {
						fres.add(f.trim());
					}
				}
			} else {
				if (fftor != null && !fftor.trim().isEmpty()) {
					fres.add(fftor.trim());
				} else {
					fres.add(fftor);
				}
			}
		}

		List<String> powersizeLs = productMapper.findAllcasePower_Size();
		List<String> psres = new ArrayList<>();
		for (String psize : powersizeLs) {
			if (psize != null && psize.contains(",")) {
				for (String f : psize.split(",")) {
					if (!psres.contains(f.trim())) {
						psres.add(f.trim());
					}
				}
			} else {
				if (psize != null && !psize.trim().isEmpty()) {
					psres.add(psize.trim());
				} else {
					psres.add(psize);
				}
			}
		}

	    attributes.put("Power_Status", productMapper.findAllcasePower_Status());
		attributes.put("Formfactor", fres);
	    attributes.put("Tower_Size", productMapper.findAllcaseTower_Size());
	    attributes.put("VGA_Length", productMapper.findAllcaseVga_Length());
	    attributes.put("Power_Size", psres);
	    attributes.put("Company", productMapper.findAllcaseCompany());
	    return ResponseEntity.ok(attributes);
	}

	//필터에 선택된 체크박스에 따라 데이터를 넘겨줌
	@PostMapping("/api/products/filter/{category}")
	public ResponseEntity<?> filterProducts(
			@PathVariable String category,
			@RequestBody Map<String, List<String>> filters,
			@RequestParam(value = "sort", defaultValue = "byname") String sort,
			@RequestParam(value = "search", required = false) String search) {

	    ProductBiz<?> productBiz = productBizFactory.getBiz(category);
	    if (productBiz == null) {
	        return ResponseEntity.badRequest().body("Invalid category: " + category);
	    }

	    try {
	        List<?> filteredProducts = productBiz.filterProducts(filters, sort, search); // 필터 및 정렬 매개변수를 비즈니스 로직에 전달
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
	        @RequestParam(value = "sort", defaultValue = "bypopular") String sort,
	        @RequestParam(value = "search", required = false) String search) {

	    System.out.println("Fetching products for category: " + category + ", sort: " + sort + ", search: " + search);
	    ProductBiz<?> productBiz = productBizFactory.getBiz(category.toLowerCase());
	    if (productBiz == null) {
	        System.err.println("No ProductBiz found for category: " + category);
	        return ResponseEntity.badRequest().body("Invalid category: " + category);
	    }

	    List<?> products = productBiz.getProductsByCategory(category, sort, search);
	    if (products.isEmpty()) {
	        return ResponseEntity.noContent().build();
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
		CustomerDto dto = customerBiz.selectCustomer(member_no);
		model.addAttribute("customer", dto);
		model.addAttribute("quote", res);

		return "customercart";
	}


	@GetMapping("/quotedetail")
	@ResponseBody
	public Map<String, Object> cartpagedetail(@RequestParam("quote_no") int quoteNo) {
		List<QuoteDetailDto> selectres = quoteBiz.QuoteDetailinfo(quoteNo);
		char res = quoteBiz.assemblecheck(quoteNo);
		System.out.println(res);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("products", selectres);  
	    responseMap.put("assemblecheck", String.valueOf(res)); 
	    System.out.println(responseMap);

		return responseMap;
	}
	
	@PostMapping("/comparison")
	@ResponseBody
	public ResponseEntity<?> comparison(@RequestBody Map<String, List<String>> selectvalue){
		List<String> values = selectvalue.get("values");
		List<QuotecomparisonDto> res = quoteBiz.Quotecomprison(values);
		Map<Integer, List<QuotecomparisonDto>> groupedData = res.stream()
		        .collect(Collectors.groupingBy(QuotecomparisonDto::getQuote_no));
		System.out.println(groupedData);
		return ResponseEntity.ok(groupedData);
	}
	
	@PostMapping("/deletequote")
	@ResponseBody
	public ResponseEntity<?> deletequote(@RequestBody Map<String, List<Integer>> selectvalue){
		List<Integer> values = selectvalue.get("values");
		int res2=quoteBiz.quotedetaildelete(values);
		int res=quoteBiz.quotedelete(values);
		
		return ResponseEntity.ok(null);
	}


	//제품 상세페이지 (상세이미지 & 리뷰)
	@GetMapping("/productDetail")
	public String popupProductDetail(
	        @RequestParam("product_code") int productCode,
	        @RequestParam(value="category", required=false) String category,
	        Model model) {
				
        ProductInfoDto dto = productInfoBiz.selectOne(productCode);
        model.addAttribute("product", dto);


        List<ReviewDto> reviewList = reviewBiz.getReviewsByProductCode(productCode);

        // 리뷰 이미지 기본값 설정
        final String defaultImage = "/img/review-default-image.png";
        for (ReviewDto review : reviewList) {
            if (review.getReviewImg() == null || review.getReviewImg().trim().isEmpty()) {
                review.setReviewImg(defaultImage); // 기본 이미지로 설정
            }
        }

        model.addAttribute("reviewList", reviewList);


	    return "popup_product_info";
	}
	
	@PostMapping("/updatequotedetailamount")
	@ResponseBody
	public ResponseEntity<?> updateQuote(@RequestBody List<Map<String, Object>> updatedData) {
	    for (Map<String, Object> data : updatedData) {
	        Integer quoteNo = Integer.parseInt((String) data.get("quoteNo"));
	        Integer amount = Integer.parseInt((String) data.get("amount"));
	        Integer productcode = Integer.parseInt((String)  data.get("productcode"));
	        quoteBiz.updatedetailamount(quoteNo,productcode, amount );
	       
	    }
	    return ResponseEntity.ok().body("저장되었습니다");
	}

	@PostMapping("/compatibility")
	@ResponseBody
	public ResponseEntity<?> compatibilityQuote(@RequestBody List<Map<String, Object>> quotedata) {
	
		return ResponseEntity.ok().body(createcompatibility(quotedata));
	}
	
	public Map<String,Object> createcompatibility(List<Map<String, Object>> quotedata){
		System.out.println(quotedata + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		boolean cpuramcompatibility = false;
		boolean cpuboardcompatibility = false;
		boolean boardmemorycompatibility = false;
		boolean boardcasecompatibility = false;
		boolean casegpucompatibility = false;
		boolean casepowerompatibility = false;

		// 각 부품에 대한 존재 여부
		boolean hascpu = false;
		boolean hasram = false;
		boolean hasmainboard = false;
		boolean hasgpu = false;
		boolean hascase = false;
		boolean haspower = false;

		int cpuproductno = 0;
		int ramproductno = 0;
		int caseproductno = 0;
		int powerproductno = 0;
		int gpuproductno = 0;
		int boardproductno = 0;
//		파워용량합산
		int wattvalue = 0;
		int powerwatt = 0;
		Map<String, Object> valueres = new HashMap<>();

		// 부품 카테고리 처리
		for (Map<String, Object> data : quotedata) {
			String category = (String) data.get("category");
			// 부품 존재 여부 체크
			switch (category) {
			case "CPU":
				hascpu = true; // CPU가 존재

				cpuproductno = Integer.parseInt((String) data.get("productcode"));
				wattvalue = wattvalue + quoteBiz.wattvalue(cpuproductno, "cpu");
				break;

			case "RAM":
				hasram = true; // RAM이 존재
				ramproductno = Integer.parseInt((String) data.get("productcode"));
				wattvalue = wattvalue + quoteBiz.wattvalue(ramproductno, "ram");
				break;

			case "메인보드":
				hasmainboard = true; // 메인보드가 존재
				boardproductno = Integer.parseInt((String) data.get("productcode"));
				wattvalue = wattvalue + quoteBiz.wattvalue(boardproductno, "mainboard");
				break;

			case "그래픽카드":
				hasgpu = true; // 그래픽카드가 존재
				gpuproductno = Integer.parseInt((String) data.get("productcode"));
				wattvalue = wattvalue + quoteBiz.wattvalue(gpuproductno, "gpu");
				break;

			case "케이스":
				hascase = true; // 케이스가 존재
				caseproductno = Integer.parseInt((String) data.get("productcode"));

				break;

			case "파워":
				haspower = true; // 파워가 존재
				powerproductno = Integer.parseInt((String) data.get("productcode"));
				powerwatt = quoteBiz.powerwatt(powerproductno);

				break;
			case "SSD":
				wattvalue = wattvalue + quoteBiz.wattvalue(Integer.parseInt((String) data.get("productcode")), "SSD");
				break;
			case "HDD":
				wattvalue = wattvalue + quoteBiz.wattvalue(Integer.parseInt((String) data.get("productcode")), "HDD");
				break;
			case "쿨러":
				wattvalue = wattvalue
						+ quoteBiz.wattvalue(Integer.parseInt((String) data.get("productcode")), "cooler");
				break;

			default:
				break;
			}
		}

//		cpu와 메모리
		if (hascpu && hasram) {
			if (quoteBiz.cpuddr(cpuproductno).toLowerCase().trim()
					.equals(quoteBiz.ramddr(ramproductno).toLowerCase().trim())) {
				cpuramcompatibility = true;
			}
			valueres.put("cpuramcompatibility", cpuramcompatibility);
		}
//		cpu와 보드
		if (hascpu && hasmainboard) {
			if (quoteBiz.boardsocket(boardproductno).toLowerCase().trim()
					.equals(quoteBiz.cpusocket(cpuproductno).toLowerCase().trim())) {
				cpuboardcompatibility = true;

			}

			valueres.put("cpuboardcompatibility", cpuboardcompatibility);
		}
//		ram과 board
		if (hasram && hasmainboard) {
			if (quoteBiz.ramddr(ramproductno).toLowerCase().trim()
					.equals(quoteBiz.boardmemoryslot(boardproductno).toLowerCase().trim())) {

				boardmemorycompatibility = true;
			}
			valueres.put("boardmemorycompatibility", boardmemorycompatibility);
		}

//		case와 board
		if (hascase && hasmainboard) {
			String caseform = quoteBiz.caseformfactor(caseproductno).toLowerCase().replace(" ","").trim();
			String boardform = quoteBiz.boardformfactor(boardproductno).toLowerCase().trim();
			String[] caseArray = caseform.split(",");

			// 배열을 리스트로 변환
			List<String> caseformlist = Arrays.asList(caseArray);
			if (caseformlist.contains(boardform)) {
				boardcasecompatibility = true;

			}
			valueres.put("boardcasecompatibility", boardcasecompatibility);

		}
//		case와 그래픽
		if (hascase && hasgpu) {
			if (quoteBiz.vgalength(gpuproductno) < quoteBiz.casevgalength(caseproductno)) {
				casegpucompatibility = true;

			}
			valueres.put("casegpucompatibility", casegpucompatibility);
		}
//		case와 power
		if (hascase && haspower) {
			String caseform = quoteBiz.casepowersize(caseproductno).toLowerCase().toLowerCase().replace(" ","").trim();
//			String casepowerform = quoteBiz.casepowersize(caseproductno).toLowerCase().trim();
			String powerform = quoteBiz.powersize(powerproductno).toLowerCase().toLowerCase().replace(" ","").trim();
			String[] caseArray = caseform.split(",");
			String[] powerArray =powerform.split(",");
			String powersize;
			// 배열을 리스트로 변환
			List<String> caseformlist = Arrays.asList(caseArray);
			List<String> powerformlist = Arrays.asList(powerArray);

			if(powerformlist.contains("atx")) {
				powersize="atx";
			}else if(powerformlist.contains("m-atx")) {
				powersize="m-atx";
			}else {
				powersize="itx";
			}
			System.out.println(powersize);

			if (caseformlist.contains(powersize)) {
				casepowerompatibility = true;
			}
			valueres.put("casepowerompatibility", casepowerompatibility);
		}

		valueres.put("wattvalue", wattvalue);
		valueres.put("powersize", powerwatt);
		System.out.println(valueres);
		
		return valueres;
		
	}
	
	@PostMapping("/cancelorder")
	public ResponseEntity<String> cancelOrder(@RequestBody Map<String, String> request) {
		int ordernum = Integer.parseInt((String) request.get("ordernum")) ;
		String cancelreason = (String) request.get("cancelreason");
		int res = orderboardBiz.requestcancelorder(ordernum, cancelreason);

		 if(res >0) {
			 return ResponseEntity.ok("취소 요청이 처리되었습니다.");
		 }else {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("취소 요청에 실패하였습니다.");
		 }


    }
	@GetMapping("/loadquotelist")
	public String getQuoteDiv(Model model,HttpSession session) {
		Integer member_no = (Integer) session.getAttribute("member_no");
		List<QuoteDto> res =quoteBiz.SelectQuote(member_no);
		model.addAttribute("quote", res);
	    return "fragmentcartquotelist :: cart_view"; // 특정 타임리프 fragment 반환
	}

	@GetMapping("/nopage")
	public String noPage() {
		return "404";
	}

}
