package com.boot.selftop_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import com.boot.selftop_web.seller.model.biz.SellerBiz;
import com.boot.selftop_web.seller.model.dto.SellerDto;


@Controller
@RequestMapping("/seller")
public class SellerController {

    @GetMapping("/sellerSignUp")
    public String showSignUpForm() {
        return "sellerSignUp";
	@Autowired
	private SellerBiz sellerbiz;
	
	@GetMapping("/main")
    public String sellermain(Model model) {
    	List<SellerDto> res = sellerbiz.selectList();
    	model.addAttribute("seller",res);
    	return "sellermain";
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

        // 약관 동의 확인
        if (!terms) {
            model.addAttribute("error", "서비스 약관과 개인정보 처리방침에 동의하셔야 합니다.");
            return "sellerSignUp";
        }
		    List<SellerDto> res = sellerbiz.selectSearch(startdate,enddate,keyword);
		    model.addAttribute("seller", res);

        model.addAttribute("message", "회원가입이 완료되었습니다.");
        return "sellerMain";
    }
}
		    return "sellermain :: tbody";
	}	

