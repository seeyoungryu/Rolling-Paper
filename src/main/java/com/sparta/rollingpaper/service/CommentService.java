package com.sparta.rollingpaper.service;

import com.sparta.rollingpaper.dto.CommentReponseDto;
import com.sparta.rollingpaper.dto.CommentRequestDto;
import com.sparta.rollingpaper.entity.Comment;
import com.sparta.rollingpaper.entity.User;
import com.sparta.rollingpaper.repository.CommentRepository;
import com.sparta.rollingpaper.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentReponseDto createComment(CommentRequestDto commentRequestDto, String currentUserId) {
        // 현재 인증된 사용자 정보 조회
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 롤링페이퍼 생성
        Comment comment = new Comment(user, commentRequestDto);
        Comment savedComment = commentRepository.save(comment);

        return new CommentReponseDto(savedComment);
    }


    // 모든 게시물 조회
    public List<CommentReponseDto> getComment() {
        // 모든 게시물 조회 후 DTO로 변환하여 반환
        return commentRepository.findAll()
                .stream()
                .map(CommentReponseDto::new)
                .collect(Collectors.toList());
    }

//        public List<RollingPaperReponseDto> getRollingPaper() {
//            // 수정일자를 기준으로 내림차순으로 모든 게시물 조회 후 DTO로 변환하여 반환
//            return rollingPaperRepository.findAllByOrderByModifiedAtDesc()
//                    .stream()
//                    .map(RollingPaperReponseDto::new)
//                    .collect(Collectors.toList());
//        }

    // CRUD methods here
}

//작성시에 예외처리 : 롤링페이퍼 작성할때 로그인안된 상태면 에러 >> 로그인이 필요합니다
