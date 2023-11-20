package com.sparta.rollingpaper.error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); // 401 코드
    }

    // 비밀번호 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse); // 400 코드
    }

//    // 사용자(ID)를 찾을 수 없거나 권한이 없을 때
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
//        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
//    }

//    // 토큰이 잘못 되었을때
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {
//        String errorMessage = e.getMessage(); // 예외 객체의 메시지를 가져옵니다.
//        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
//    }
}
