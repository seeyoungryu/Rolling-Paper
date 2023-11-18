package com.sparta.rollingpaper.repository;

import com.sparta.rollingpaper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // 기본적인 CRUD 메서드와 함께 findById가 사용 가능
    // 이름을 찾는 코드도 추가
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserId(String userId);
}
