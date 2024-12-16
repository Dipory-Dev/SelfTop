package com.boot.selftop_web.controller;

import com.boot.selftop_web.seller.model.biz.AdminBiz;
import com.boot.selftop_web.seller.model.biz.product.CoolerBiz;
import com.boot.selftop_web.seller.model.biz.product.CoolerBizImpl;
import com.boot.selftop_web.seller.model.dto.AdminDto;
import com.boot.selftop_web.seller.model.dto.product.CoolerDto;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminBiz adminBiz;
    @Autowired
    CoolerBiz coolerBiz = new CoolerBizImpl();

    @GetMapping("/")
    public String redirectToLogin(HttpSession session) {
        AdminDto admin = (AdminDto) session.getAttribute("admin");
        if (admin != null) {
            return "redirect:/admin/main";
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/main")
    public String main(HttpSession session) {
        AdminDto admin = (AdminDto) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }
        return "adminMain";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "adminLoginForm";
    }

    @PostMapping("/loginchk")
    public String loginCheck(@RequestParam String id, @RequestParam String password, HttpSession session, Model model) {
        AdminDto admin = adminBiz.login(id, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/main";
        } else {
            model.addAttribute("msg", "아이디 또는 비밀번호를 확인하세요.");
            return "adminLoginForm";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @PostMapping("/add")
    @ResponseBody // JSON 응답을 반환
    public Map<String, Object> addProduct(@RequestParam("category") String category, CoolerDto dto) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("Controller : " + category);
            System.out.println("Controller : " + dto);

            int res = coolerBiz.insert(dto);
            System.out.println("Controller : " + res);

            if (res > 0) {
                response.put("success", true);
                response.put("message", "제품이 성공적으로 추가되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "제품 추가에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "서버 처리 중 오류가 발생했습니다.");
        }

        return response;
    }
}
