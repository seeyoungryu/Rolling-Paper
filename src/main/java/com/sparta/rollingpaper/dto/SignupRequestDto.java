package com.sparta.rollingpaper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank
    private String userId; // 기본 키로 사용(PK)

    @NotBlank
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,12}$",
            message = "비밀번호는 4~12자리의 영문 대소문자와 숫자의 조합이어야 합니다.")
    private String password;

    @NotBlank(message = "이름을 정확히 작성해주세요")
    private String userName;
}
