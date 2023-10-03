package com.example.formloginwithoutsecurity.vo;

import com.example.formloginwithoutsecurity.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthVO {

    private String userId;

    private String userPwd;

    private Role role;
}
