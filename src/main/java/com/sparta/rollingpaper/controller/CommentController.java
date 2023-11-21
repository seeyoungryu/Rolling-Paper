package com.sparta.rollingpaper.controller;

import com.sparta.rollingpaper.dto.CommentListResponseDto;
import com.sparta.rollingpaper.dto.CommentReponseDto;
import com.sparta.rollingpaper.dto.CommentRequestDto;
import com.sparta.rollingpaper.secuity.UserDetailsImpl;
import com.sparta.rollingpaper.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
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
    private final CommentService commentService;

    @PostMapping("/users/comments")
    public ResponseEntity<CommentReponseDto> createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            Authentication authentication) {
        // 현재 인증된 사용자의 ID를 가져옴
        String currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        // 현재 인증된 사용자 정보를 사용하여 롤링페이퍼 생성
        CommentReponseDto createdComment = commentService.createComment(commentRequestDto, currentUserId);
        return ResponseEntity.status(HttpStatus.OK).body(createdComment);
    }

    // 모든 게시물 조회 API
    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<Object> getCommentsByUserId(@PathVariable String userId) {
        try {
            CommentListResponseDto responseDto = commentService.getCommentsByUserId(userId);
            return ResponseEntity.ok(responseDto);
        } catch (EntityNotFoundException e) {
            // 사용자가 존재하지 않을 때 오류 메시지 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("없는 사용자입니다. ID를 다시 확인해 주세요.");
        }
    }

    // comment 삭제
    @DeleteMapping("/users/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, Authentication authentication) {
        try {
            String currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
            commentService.deleteComment(commentId, currentUserId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}