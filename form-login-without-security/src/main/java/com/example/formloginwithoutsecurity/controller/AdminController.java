package com.example.formloginwithoutsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    /**
     * 어드민 페이지
     *
     * @return
     */
    @GetMapping("/")
    public String adminPage() {
        return "admin";
    }
}
