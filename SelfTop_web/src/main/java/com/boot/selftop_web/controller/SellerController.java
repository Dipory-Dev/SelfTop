package com.boot.selftop_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	@Autowired
	private SellerBiz sellerbiz;
	
	@GetMapping("/main")
    public String sellermain(Model model) {
    	List<SellerDto> res = sellerbiz.selectList();
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

		    List<SellerDto> res = sellerbiz.selectSearch(startdate,enddate,keyword);
		    model.addAttribute("seller", res);

		    return "sellermain :: tbody";
	}	

}
