package com.sparta.rollingpaper.controller;

import com.sparta.rollingpaper.dto.RollingPaperReponseDto;
import com.sparta.rollingpaper.dto.RollingPaperRequestDto;
import com.sparta.rollingpaper.secuity.UserDetailsImpl;
import com.sparta.rollingpaper.service.RollingPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RollingPaperController {
    private final RollingPaperService rollingPaperService;


    // CRUD endpoints here


    @PostMapping("/user/comments")
    public ResponseEntity<RollingPaperReponseDto> createRollingPaper(
            @RequestBody RollingPaperRequestDto rollingPaperRequestDto,
            Authentication authentication) {
        // 현재 인증된 사용자의 ID를 가져옴
        String currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        // 현재 인증된 사용자 정보를 사용하여 롤링페이퍼 생성
        RollingPaperReponseDto createdRollingPaper = rollingPaperService.createRollingPaper(rollingPaperRequestDto, currentUserId);
        return ResponseEntity.status(HttpStatus.OK).body(createdRollingPaper);
    }



    // 모든 게시물 조회 API
    @GetMapping("/rollingpapers/comments")
    public ResponseEntity<List<RollingPaperReponseDto>> getRollingpapers() {
        List<RollingPaperReponseDto> rollingPapers = rollingPaperService.getRollingPaper();
        return new ResponseEntity<>(rollingPapers, HttpStatus.OK);
    }

}


//상태코드

//    @PostMapping("/user/bio/{userId}")
//    public ResponseEntity<BioResponseDto> createBio(@PathVariable String userId,
//                                                    @RequestBody BioRequestDto bioRequestDto,
//                                                    Authentication authentication) {
//        String currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
//        BioResponseDto bioResponseDto = userService.createBio(currentUserId, bioRequestDto);
//        return ResponseEntity.status(HttpStatus.OK).body(bioResponseDto);
//    }