package com.sparta.rollingpaper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String userId; // 기본 키로 사용(PK)

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(length = 500) // 길이 설정
    private String bio; // 자기소개 필드
}
