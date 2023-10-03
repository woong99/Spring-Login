package com.example.formloginwithoutsecurity.repository;

import com.example.formloginwithoutsecurity.entity.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByUserId(String userId);
}
