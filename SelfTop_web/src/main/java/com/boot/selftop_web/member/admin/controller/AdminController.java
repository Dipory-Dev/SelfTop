package com.boot.selftop_web.member.admin.controller;

import com.boot.selftop_web.member.admin.biz.AdminBiz;
import com.boot.selftop_web.product.biz.ProductBiz;
import com.boot.selftop_web.product.biz.ProductBizFactory;
import com.boot.selftop_web.member.admin.model.dto.AdminDto;
import com.boot.selftop_web.product.model.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminBiz adminBiz;
    @Autowired
    private ProductBizFactory productBizFactory;

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
    @ResponseBody
    public Map<String, Object> addProduct(@RequestParam("category") String category, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        ProductBiz biz = productBizFactory.getBiz(category);

        if (biz == null) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }

        switch (category) {
            case "CPU":
                CPUDto cpuDto = new CPUDto();
                cpuDto.setCategory(request.getParameter("category"));
                cpuDto.setProduct_name(request.getParameter("product_name"));
                cpuDto.setCompany(request.getParameter("company"));
                cpuDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                cpuDto.setThumbnail(request.getParameter("thumbnail"));
                cpuDto.setContent_img(request.getParameter("content_img"));
                cpuDto.setEtc(request.getParameter("etc"));

                cpuDto.setSocket(request.getParameter("socket"));
                cpuDto.setDdr(request.getParameter("ddr"));
                cpuDto.setGeneration(request.getParameter("generation"));
                cpuDto.setSpec(request.getParameter("spec"));
                cpuDto.setInner_vga(request.getParameter("inner_vga"));
                cpuDto.setPackage_type(request.getParameter("package_type"));
                cpuDto.setCooler_status(request.getParameter("cooler_status"));
                cpuDto.setCore(Integer.parseInt(request.getParameter("core")));
                cpuDto.setWatt(Integer.parseInt(request.getParameter("watt")));
                response = sendMSG(biz, cpuDto);
                break;
            case "RAM":
                RAMDto ramDto = new RAMDto();
                ramDto.setCategory(request.getParameter("category"));
                ramDto.setProduct_name(request.getParameter("product_name"));
                ramDto.setCompany(request.getParameter("company"));
                ramDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                ramDto.setThumbnail(request.getParameter("thumbnail"));
                ramDto.setContent_img(request.getParameter("content_img"));
                ramDto.setEtc(request.getParameter("etc"));

                ramDto.setDdr(request.getParameter("ddr"));
                ramDto.setStorage(Integer.parseInt(request.getParameter("storage")));
                ramDto.setDevice(request.getParameter("device"));
                ramDto.setHeatsync(request.getParameter("heatsync"));
                ramDto.setWatt(Integer.parseInt(request.getParameter("watt")));
                response = sendMSG(biz, ramDto);
                break;
            case "메인보드":
                MainBoardDto mainBoardDto = new MainBoardDto();
                mainBoardDto.setCategory(request.getParameter("category"));
                mainBoardDto.setProduct_name(request.getParameter("product_name"));
                mainBoardDto.setCompany(request.getParameter("company"));
                mainBoardDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                mainBoardDto.setThumbnail(request.getParameter("thumbnail"));
                mainBoardDto.setContent_img(request.getParameter("content_img"));
                mainBoardDto.setEtc(request.getParameter("etc"));

                mainBoardDto.setSocket(request.getParameter("socket"));
                mainBoardDto.setFormfactor(request.getParameter("formfactor"));
                mainBoardDto.setMemory_slot(Integer.parseInt(request.getParameter("memory_slot")));
                mainBoardDto.setDdr(request.getParameter("ddr"));
                mainBoardDto.setMax_storage(Integer.parseInt(request.getParameter("max_storage")));
                mainBoardDto.setWatt(Integer.parseInt(request.getParameter("watt")));
                response = sendMSG(biz, mainBoardDto);
                break;
            case "케이스":
                CaseDto caseDto = new CaseDto();
                caseDto.setCategory(request.getParameter("category"));
                caseDto.setProduct_name(request.getParameter("product_name"));
                caseDto.setCompany(request.getParameter("company"));
                caseDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                caseDto.setThumbnail(request.getParameter("thumbnail"));
                caseDto.setContent_img(request.getParameter("content_img"));
                caseDto.setEtc(request.getParameter("etc"));

                caseDto.setPower_status(request.getParameter("power_status"));
                caseDto.setFormfactor(request.getParameter("formfactor"));
                caseDto.setPower_size(request.getParameter("powerformfactor"));
                caseDto.setTower_size(request.getParameter("tower_size"));
                caseDto.setVga_length(Integer.parseInt(request.getParameter("vga_length")));
                response = sendMSG(biz, caseDto);
                break;
            case "그래픽카드":
                GPUDto gpuDto = new GPUDto();
                gpuDto.setCategory(request.getParameter("category"));
                gpuDto.setProduct_name(request.getParameter("product_name"));
                gpuDto.setCompany(request.getParameter("company"));
                gpuDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                gpuDto.setThumbnail(request.getParameter("thumbnail"));
                gpuDto.setContent_img(request.getParameter("content_img"));
                gpuDto.setEtc(request.getParameter("etc"));

                gpuDto.setSeries(request.getParameter("series"));
                gpuDto.setStorage(Integer.parseInt(request.getParameter("storage")));
                gpuDto.setLength(Integer.parseInt(request.getParameter("length")));
                gpuDto.setWatt(Integer.parseInt(request.getParameter("watt")));
                response = sendMSG(biz, gpuDto);
                break;
            case "파워":
                PowerDto powerDto = new PowerDto();
                powerDto.setCategory(request.getParameter("category"));
                powerDto.setProduct_name(request.getParameter("product_name"));
                powerDto.setCompany(request.getParameter("company"));
                powerDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                powerDto.setThumbnail(request.getParameter("thumbnail"));
                powerDto.setContent_img(request.getParameter("content_img"));
                powerDto.setEtc(request.getParameter("etc"));

                powerDto.setSupply(Integer.parseInt(request.getParameter("supply")));
                powerDto.setPlus80(request.getParameter("plus80"));
                powerDto.setFormfactor(request.getParameter("formfactor"));
                response = sendMSG(biz, powerDto);
                break;
            case "SSD":
                SSDDto ssdDto = new SSDDto();
                ssdDto.setCategory(request.getParameter("category"));
                ssdDto.setProduct_name(request.getParameter("product_name"));
                ssdDto.setCompany(request.getParameter("company"));
                ssdDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                ssdDto.setThumbnail(request.getParameter("thumbnail"));
                ssdDto.setContent_img(request.getParameter("content_img"));
                ssdDto.setEtc(request.getParameter("etc"));

                ssdDto.setStorage(Integer.parseInt(request.getParameter("storage")));
                ssdDto.setType(request.getParameter("type"));
                ssdDto.setWatt(Integer.parseInt(request.getParameter("watt")));
                response = sendMSG(biz, ssdDto);
                break;
            case "HDD":
                HDDDto hddDto = new HDDDto();
                hddDto.setCategory(request.getParameter("category"));
                hddDto.setProduct_name(request.getParameter("product_name"));
                hddDto.setCompany(request.getParameter("company"));
                hddDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                hddDto.setThumbnail(request.getParameter("thumbnail"));
                hddDto.setContent_img(request.getParameter("content_img"));
                hddDto.setEtc(request.getParameter("etc"));

                hddDto.setDevice(request.getParameter("device"));
                hddDto.setStorage(Integer.parseInt(request.getParameter("storage")));
                hddDto.setWatt(Integer.parseInt(request.getParameter("watt")));
                response = sendMSG(biz, hddDto);
                break;
            case "쿨러":
                CoolerDto coolerDto = new CoolerDto();
                coolerDto.setCategory(request.getParameter("category"));
                coolerDto.setProduct_name(request.getParameter("product_name"));
                coolerDto.setCompany(request.getParameter("company"));
                coolerDto.setUpload_date(parseDate(request.getParameter("upload_date")));
                coolerDto.setThumbnail(request.getParameter("thumbnail"));
                coolerDto.setContent_img(request.getParameter("content_img"));
                coolerDto.setEtc(request.getParameter("etc"));

                coolerDto.setCooler_type(request.getParameter("cooler_type"));
                coolerDto.setSocket(request.getParameter("socket"));
                coolerDto.setWatt(Integer.parseInt(request.getParameter("watt")));
                biz.insert(coolerDto);
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
        return response;
    }

    // 날짜 형변환 메서드
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @ResponseBody // JSON 응답을 반환
    public Map<String, Object> sendMSG(ProductBiz biz, ProductDto dto) {
        System.out.println("sendMSG" + dto);
        Map<String, Object> response = new HashMap<>();
        try {
            int res = biz.insert(dto);
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
