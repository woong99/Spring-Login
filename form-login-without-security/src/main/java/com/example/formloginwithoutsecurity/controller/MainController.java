package com.example.formloginwithoutsecurity.controller;

import com.example.formloginwithoutsecurity.entity.Auth;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        Auth auth = (Auth) request.getSession().getAttribute("auth");
        if (auth == null) {
            return "redirect:/auth/login";
        }
        return "/home";
    }
}
