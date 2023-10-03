package com.example.formloginwithoutsecurity.controller;

import com.example.formloginwithoutsecurity.entity.Auth;
import com.example.formloginwithoutsecurity.service.AuthService;
import com.example.formloginwithoutsecurity.vo.AuthVO;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        Auth auth = (Auth) request.getSession().getAttribute("auth");
        if (auth != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/signUp")
    public String signUpPage(HttpServletRequest request) {
        Auth auth = (Auth) request.getSession().getAttribute("auth");
        if (auth != null) {
            return "redirect:/";
        }
        return "signUp";
    }

    @PostMapping("/signUp.do")
    public String signUp(AuthVO authVO, HttpServletRequest request, ModelMap model) {
        Auth auth = (Auth) request.getSession().getAttribute("auth");
        if (auth != null) {
            return "redirect:/";
        }
        try {
            authService.signUp(authVO);
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", "회원가입에 실패하였습니다.");
            model.addAttribute("retType", "message");
            model.addAttribute("retUrl", "/auth/signUp");
            return "message";
        } catch (EntityExistsException e) {
            model.addAttribute("message", "이미 존재하는 아이디입니다.");
            model.addAttribute("retType", "message");
            model.addAttribute("retUrl", "/auth/signUp");
            return "message";
        }
        model.addAttribute("message", "회원가입에 성공하였습니다.");
        model.addAttribute("retType", "message");
        model.addAttribute("retUrl", "/auth/login");
        return "message";
    }

    @PostMapping("/login.do")
    public String login(AuthVO authVO, HttpServletRequest request, ModelMap model) {
        Auth auth = (Auth) request.getSession().getAttribute("auth");
        if (auth != null) {
            return "redirect:/";
        }
        Auth savedAuth;
        try {
            savedAuth = authService.login(authVO);
        } catch (Exception e) {
            model.addAttribute("message", "로그인에 실패하였습니다.");
            model.addAttribute("retType", "message");
            model.addAttribute("retUrl", "/auth/login");
            return "message";
        }
        request.getSession().setAttribute("auth", savedAuth);
        return "redirect:/";
    }

    @GetMapping("/logout.do")
    public String logout(HttpServletRequest request) {
        Auth auth = (Auth) request.getSession().getAttribute("auth");
        if (auth == null) {
            return "redirect:/auth/login";
        }
        request.getSession().removeAttribute("auth");
        return "redirect:/auth/login";
    }
}
