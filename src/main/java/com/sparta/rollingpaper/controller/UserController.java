package com.sparta.rollingpaper.controller;

import com.sparta.rollingpaper.dto.BioRequestDto;
import com.sparta.rollingpaper.dto.BioResponseDto;
import com.sparta.rollingpaper.dto.SignupRequestDto;
import com.sparta.rollingpaper.entity.User;
import com.sparta.rollingpaper.secuity.UserDetailsImpl;
import com.sparta.rollingpaper.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok().build(); // 상태 코드 200만 반환
    }

    // 자기소개 작성
    // API 요청이 들어올 때, 현재 인증된 사용자의 ID가 요청과 일치하는지 확인
    // Authentication 객체를 사용하여 현재 인증된 사용자의 정보를 가져오게 수정
    @PostMapping("/users/bio")
    public ResponseEntity<BioResponseDto> createBio(@RequestBody BioRequestDto bioRequestDto,
                                                    Authentication authentication) {
        String currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        BioResponseDto bioResponseDto = userService.createBio(currentUserId, bioRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(bioResponseDto);
    }

    // 자기소개 목록 조회
    @GetMapping("/users")
    public ResponseEntity<List<BioResponseDto>> getAllBios() {
        List<BioResponseDto> bios = userService.getAllUserBios();
        return ResponseEntity.ok(bios);
    }
}
