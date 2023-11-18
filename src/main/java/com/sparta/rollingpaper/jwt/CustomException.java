package com.sparta.rollingpaper.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

//    public CustomException(ErrorCode errorCode) {
//        super(errorCode.getMessage()); // 부모 클래스의 생성자 호출
//        this.errorCode = errorCode;
//    }

    // Getter 메서드 등 필요한 메서드 추가
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
