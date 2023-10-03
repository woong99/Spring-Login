package com.example.formloginwithoutsecurity.controller;

import com.example.formloginwithoutsecurity.entity.Auth;
import com.example.formloginwithoutsecurity.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/")
    public String adminPage(HttpServletRequest request) {
        Auth auth = (Auth) request.getSession().getAttribute("auth");
        if (auth == null || !auth.getRole().equals(Role.ADMIN)) {
            return "redirect:/";
        }
        return "admin";
    }
}
