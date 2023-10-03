package com.example.formloginwithoutsecurity.entity;

import com.example.formloginwithoutsecurity.vo.AuthVO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String userPwd;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public static Auth toEntity(AuthVO authVO) {
        return Auth.builder()
                .userId(authVO.getUserId())
                .userPwd(authVO.getUserPwd())
                .role(authVO.getRole())
                .build();
    }
}
