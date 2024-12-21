package com.boot.selftop_web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object roleObj = session.getAttribute("role");

        String requestURI = request.getRequestURI();

        // 정적 리소스(CSS, JS, 이미지 등) 요청은 바로 허용
        if (requestURI.startsWith("/css/") || requestURI.startsWith("/js/") || requestURI.startsWith("/img/")) {
            return true; // 요청 계속 진행
        }

        // 로그아웃 상태 처리
        if (roleObj == null) {
            return true; // 요청 계속 진행
        }

        // 로그인 상태 처리
        char role = (char) roleObj;

        // delUser와 changepw는 모두 허용, 루트 페이지로 리다이렉트
        if (requestURI.equals("/delUser") || requestURI.equals("/changepw")) {
            return true; // 요청 계속 진행
        }

        // 기본적인 권한 로직만 처리
        if (role == 'S' && !request.getRequestURI().startsWith("/seller")) {
            response.sendRedirect("/seller/main");
            return false;
        }

        // 기본적으로 모든 요청 허용
        return true;
    }
}
