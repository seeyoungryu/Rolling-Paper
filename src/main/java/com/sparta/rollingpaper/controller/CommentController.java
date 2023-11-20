package com.sparta.rollingpaper.controller;

import com.sparta.rollingpaper.dto.CommentReponseDto;
import com.sparta.rollingpaper.dto.CommentRequestDto;
import com.sparta.rollingpaper.secuity.UserDetailsImpl;
import com.sparta.rollingpaper.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService CommentService;

    @PostMapping("/users/comments")
    public ResponseEntity<CommentReponseDto> createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            Authentication authentication) {
        // 현재 인증된 사용자의 ID를 가져옴
        String currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        // 현재 인증된 사용자 정보를 사용하여 롤링페이퍼 생성
        CommentReponseDto createdComment = CommentService.createComment(commentRequestDto, currentUserId);
        return ResponseEntity.status(HttpStatus.OK).body(createdComment);
    }

    // 모든 게시물 조회 API
    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<List<CommentReponseDto>> getComments() {
        List<CommentReponseDto> comments = CommentService.getComment();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}