package com.example.formloginwithoutsecurity.service;

import com.example.formloginwithoutsecurity.common.RSA;
import com.example.formloginwithoutsecurity.entity.Auth;
import com.example.formloginwithoutsecurity.entity.Role;
import com.example.formloginwithoutsecurity.repository.AuthRepository;
import com.example.formloginwithoutsecurity.vo.AuthVO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    public void signUp(AuthVO authVO) {
        if (authVO == null || !StringUtils.hasText(authVO.getUserId()) || !StringUtils.hasText(authVO.getUserPwd())) {
            throw new IllegalArgumentException();
        }

        Optional<Auth> savedAuth = authRepository.findByUserId(authVO.getUserId());
        if (savedAuth.isPresent()) {
            throw new EntityExistsException();
        }
        authVO.setUserPwd(passwordEncoder.encode(authVO.getUserPwd()));
        authVO.setRole(Role.USER);
        authRepository.save(Auth.toEntity(authVO));
    }

    public Auth login(AuthVO authVO, HttpServletRequest request) throws Exception {
        if (authVO == null || !StringUtils.hasText(authVO.getUserId()) || !StringUtils.hasText(authVO.getUserPwd())) {
            throw new IllegalArgumentException();
        }

        Auth savedAuth = authRepository.findByUserId(authVO.getUserId()).orElseThrow(EntityNotFoundException::new);

        // 비밀번호 복호화
        PrivateKey privateKey = (PrivateKey) request.getSession().getAttribute(RSA.RSA_WEB_KEY);
        String pwd = RSA.decryptRsa(privateKey, authVO.getUserPwd());

        if (!passwordEncoder.matches(pwd, savedAuth.getUserPwd())) {
            throw new IllegalArgumentException();
        }
        return savedAuth;
    }
}
