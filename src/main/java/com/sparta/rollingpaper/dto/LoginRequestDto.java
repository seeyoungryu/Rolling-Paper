package com.sparta.rollingpaper.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String userId;
    private String password;
}
