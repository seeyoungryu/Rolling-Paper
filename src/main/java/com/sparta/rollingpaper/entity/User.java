package com.sparta.rollingpaper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
//@Builder
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
}
