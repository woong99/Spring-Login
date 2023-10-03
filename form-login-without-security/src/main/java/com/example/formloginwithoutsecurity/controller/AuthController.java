package com.example.formloginwithoutsecurity.controller;

import com.example.formloginwithoutsecurity.common.MessageUtil;
import com.example.formloginwithoutsecurity.common.RSA;
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

    /**
     * 로그인 페이지
     *
     * @return
     */
    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        RSA.initRsa(request);
        return "login";
    }

    /**
     * 회원가입 페이지
     *
     * @return
     */
    @GetMapping("/signUp")
    public String signUpPage() {
        return "signUp";
    }

    /**
     * 회원가입 action
     *
     * @param authVO
     * @param model
     * @return
     */
    @PostMapping("/signUp.do")
    public String signUp(AuthVO authVO, ModelMap model) {
        try {
            authService.signUp(authVO);
        } catch (IllegalArgumentException e) {
            return MessageUtil.createMessage("회원가입에 실패하였습니다.", "/auth/signUp", model);
        } catch (EntityExistsException e) {
            return MessageUtil.createMessage("이미 존재하는 아이디입니다.", "/auth/signUp", model);
        }
        return MessageUtil.createMessage("회원가입에 성공하였습니다.", "/auth/login", model);
    }

    /**
     * 로그인 action
     *
     * @param authVO
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/login.do")
    public String login(AuthVO authVO, HttpServletRequest request, ModelMap model) {
        Auth savedAuth;
        try {
            savedAuth = authService.login(authVO, request);
        } catch (Exception e) {
            return MessageUtil.createMessage("로그인에 실패하였습니다.", "/auth/login", model);
        }
        request.getSession().setAttribute("auth", savedAuth);
        return "redirect:/";
    }

    /**
     * 로그아웃 action
     *
     * @param request
     * @return
     */
    @GetMapping("/logout.do")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("auth");
        return "redirect:/auth/login";
    }
}
