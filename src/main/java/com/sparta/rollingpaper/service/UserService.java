package com.sparta.rollingpaper.service;

import com.sparta.rollingpaper.dto.BioRequestDto;
import com.sparta.rollingpaper.dto.BioResponseDto;
import com.sparta.rollingpaper.dto.LoginRequestDto;
import com.sparta.rollingpaper.dto.SignupRequestDto;
import com.sparta.rollingpaper.entity.User;
import com.sparta.rollingpaper.entity.UserEnum;
import com.sparta.rollingpaper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public User signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String userName = requestDto.getUserName();

        // userName이 허용된 사용자 명단에 있는지 확인
        if (!UserEnum.contains(userName)) {
            throw new IllegalArgumentException("가입할 수 없는 사용자 이름입니다.");
        }

        // userId 중복 확인
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }

        // userName 중복 확인
        if (userRepository.findByUserName(userName).isPresent()) {
            throw new IllegalArgumentException("가입한 사용자가 존재합니다.");
        }

        // 사용자 정보 저장
        User user = User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .bio(" ") // 초기값 설정
                .build();
        userRepository.save(user);
        return user;
    }

    // 자기소개 작성
    @Transactional
    public BioResponseDto updateBio(String currentUserId,BioRequestDto bioRequestDto) {
        // 권한 검증
        if (!currentUserId.equals(bioRequestDto.getUserId())) {
            throw new AccessDeniedException("다른 사용자의 정보에 접근할 수 없습니다.");
        }

        User user = userRepository.findById(bioRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setBio(bioRequestDto.getBio());
        userRepository.save(user);

        return new BioResponseDto(user.getUserId(), user.getUserName(), user.getBio());
    }

    // 메인페이지에 자기소개 목록 조회
    public List<BioResponseDto> getAllUserBios() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new BioResponseDto(user.getUserId(), user.getUserName(), user.getBio()))
                .collect(Collectors.toList());
    }
}
