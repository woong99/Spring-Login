package com.example.formloginwithoutsecurity.controller;

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

    /**
     * 메인 페이지(일반, 어드민 접근 가능)
     *
     * @return
     */
    @GetMapping("/")
    public String home() {
        return "/home";
    }
}
