package com.example.formloginwithoutsecurity.config;

import com.example.formloginwithoutsecurity.entity.Auth;
import com.example.formloginwithoutsecurity.entity.Role;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@Slf4j
@Component
public class AuthFilter implements Filter {

    private static final String[] adminUrl = {"/admin/**"};

    private static final String[] noAuthUrl = {"/auth/login", "/auth/signUp", "/auth/login.do", "/auth/signUp.do"};

    private static final String[] userUrl = {"/auth/logout.do", "/"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Auth auth = (Auth) session.getAttribute("auth");
        String requestURI = req.getRequestURI();

        // ADMIN Role
        if (PatternMatchUtils.simpleMatch(adminUrl, requestURI) && (auth == null || !auth.getRole()
                .equals(Role.ADMIN))) {
            res.sendRedirect("/auth/login");
        }

        // No Auth
        if (PatternMatchUtils.simpleMatch(noAuthUrl, requestURI) && (auth != null)) {
            res.sendRedirect("/");
        }

        // USER Role
        if (PatternMatchUtils.simpleMatch(userUrl, requestURI) && (auth == null)) {
            res.sendRedirect("/auth/login");
        }
        chain.doFilter(request, response);
    }
}
